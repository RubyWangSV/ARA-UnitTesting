package app.car;

import spark.*;

public CarController (final CarService carService) {

	get("/api/v1/cars", (req, res) -> carService.getAllCars(), json());

	get("/api/v1/cars/:id", (req, res) -> {
			String id = req.params(":id");
			Car car = carService.getOneCar(id);
			if (car != null) {
				// car.save();
				return car;
			}
			res.status(400);
			return new ResponseError("No car with id '%s' found", id);
		}, json());
}
