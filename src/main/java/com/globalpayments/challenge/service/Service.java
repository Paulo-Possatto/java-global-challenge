package com.globalpayments.challenge.service;

import com.globalpayments.challenge.dto.RequestDto;
import com.globalpayments.challenge.dto.ResponseDto;
import com.globalpayments.challenge.entity.CustomerEntity;
import com.globalpayments.challenge.error.ErrorType;
import com.globalpayments.challenge.exception.CarNameException;
import com.globalpayments.challenge.exception.CarTypeException;
import com.globalpayments.challenge.exception.CustomerException;
import com.globalpayments.challenge.helper.ConstantHelper;
import com.globalpayments.challenge.repository.CarRepository;
import com.globalpayments.challenge.repository.CustomerRepository;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    private final CarRepository carRepository;

    private final CustomerRepository customerRepository;

    public Service(CarRepository carRepository, CustomerRepository customerRepository) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
    }

    public double priceForPremiumExtraDay(int daysOff){
        return (300 * daysOff) * 1.2;
    }

    public double priceForSuv(int daysRented){
        if(daysRented <= 7){
            return daysRented * 150;
        }
        if(daysRented <= 30){
            return 7 * 150 + ((daysRented - 7) * 0.8 * 150);
        }
        return 7 * 150 + (23 * 0.8 * 150) + ((daysRented - 30) * 0.5 * 150);
    }

    public double priceForSuvExtraDays(int daysOff, int daysRented){
        return (priceForSuv(daysRented)) + (50 * daysOff * 0.6);
    }

    public double priceForSmallCar(int daysRented){
        if(daysRented <= 7){
            return daysRented * 50;
        }
        return 7 * 50 + ((daysRented - 7) * 50 * 0.6);
    }

    public double priceForSmallCarsExtraDays(int daysRented){
        return 1.3 * priceForSmallCar(daysRented);
    }

    public Object getCar(List<RequestDto> cars){
        Object res;
        try{
            List<ResponseDto> responseCars = new ArrayList<>();
            for(RequestDto car : cars) {
                if (!car.getCarType().equals("Premium") &&
                        !car.getCarType().equals("SUV") &&
                        !car.getCarType().equals("Small")) {
                    throw new CarTypeException(ConstantHelper.CAR_TYPE_NOT_EXIST);
                }
                if (!carRepository.isCarExisting(car.getCarName())) {
                    throw new CarNameException(ConstantHelper.CAR_NAME_DOES_NOT_EXIST);
                }
                if(!customerRepository.isCustomerExisting(car.getCustomerId())){
                    throw new CustomerException(ConstantHelper.CUSTOMER_DOES_NOT_EXIST);
                }
                int customerTotalPoints = Integer.parseInt(customerRepository.totalCustomerPoints(car.getCustomerId()));
                ResponseDto responseDto = new ResponseDto();
                responseDto.setCarName(car.getCarName());
                responseDto.setCarType(car.getCarType());
                int lateDays = Math.max(car.getDaysRented() - car.getDaysRequested(), 0);
                System.out.println(lateDays);
                CustomerEntity customerEntity = new CustomerEntity();
                customerEntity.setId(car.getCustomerId());
                if (car.getCarType().equalsIgnoreCase("Premium")) {
                    if (lateDays > 0) {
                        responseDto.setPrice(300 * car.getDaysRequested() + priceForPremiumExtraDay(lateDays));
                    } else {
                        responseDto.setPrice(300 * car.getDaysRented());
                    }
                    customerEntity.setCustPoints(String.valueOf(customerTotalPoints + 5));
                }
                if (car.getCarType().equalsIgnoreCase("SUV")) {
                    if (lateDays > 0) {
                        responseDto.setPrice(priceForSuv(car.getDaysRequested()) + priceForSuvExtraDays(lateDays, car.getDaysRequested()));
                    } else {
                        responseDto.setPrice(priceForSuv(car.getDaysRequested()));
                    }
                    customerEntity.setCustPoints(String.valueOf(customerTotalPoints + 3));
                }
                if (car.getCarType().equalsIgnoreCase("Small")) {
                    if (lateDays > 0) {
                        responseDto.setPrice(priceForSmallCarsExtraDays(car.getDaysRented()));
                    } else {
                        responseDto.setPrice(priceForSmallCar(car.getDaysRequested()));
                    }
                    customerEntity.setCustPoints(String.valueOf(customerTotalPoints + 1));
                }
                customerRepository.save(customerEntity);
                responseCars.add(responseDto);
            }
            res = responseCars;
        } catch (CarTypeException e) {
            res = new ErrorType(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (CustomerException | CarNameException e) {
            res = new ErrorType(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return res;
    }
}
