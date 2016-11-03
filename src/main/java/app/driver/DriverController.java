package app.driver;

import spark.*;

public DriverController (final DriverService driverService) {

	get("/api/v1/drivers", (req, res) -> driverService.getAllDrivers(), json());

	get("/api/v1/drivers/:id", (req, res) -> {
			String id = req.params(":id");
			Driver driver = driverService.getOnedriver(id);
			if (driver != null) {
				// driver.save();
				return driver;
			}
			res.status(400);
			return new ResponseError("No driver with id '%s' found", id);
		}, json());
}
