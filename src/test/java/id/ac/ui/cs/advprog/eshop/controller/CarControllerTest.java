package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @Mock
    private CarServiceImpl carService;

    @Mock
    private Model model;

    @InjectMocks
    private CarController carController;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car.setCarName("Tesla");
        car.setCarColor("Black");
        car.setCarQuantity(10);
    }

    @Test
    void testCreateCarPage() {
        String viewName = carController.createCarPage(model);

        assertEquals("CreateCar", viewName);
        verify(model).addAttribute(eq("car"), any(Car.class));
    }

    @Test
    void testCreateCarPost() {
        String viewName = carController.createCarPost(car, model);

        assertEquals("redirect:listCar", viewName);
        verify(carService).create(car);
    }

    @Test
    void testCarListPage() {
        List<Car> carList = new ArrayList<>();
        carList.add(car);
        when(carService.findAll()).thenReturn(carList);

        String viewName = carController.carListPage(model);

        assertEquals("CarList", viewName);
        verify(model).addAttribute("cars", carList);
    }

    @Test
    void testEditCarPage() {
        when(carService.findById(car.getCarId())).thenReturn(car);

        String viewName = carController.editCarPage(car.getCarId(), model);

        assertEquals("EditCar", viewName);
        verify(model).addAttribute("car", car);
    }

    @Test
    void testEditCarPost() {
        String viewName = carController.editCarPost(car, model);

        assertEquals("redirect:listCar", viewName);
        verify(carService).update(car.getCarId(), car);
    }

    @Test
    void testDeleteCar() {
        String viewName = carController.deleteCar(car.getCarId());

        assertEquals("redirect:listCar", viewName);
        verify(carService).deleteCarById(car.getCarId());
    }
}