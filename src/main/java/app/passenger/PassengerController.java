package app.passenger;

import spark.*;

public PassengerController (final PassengerService passengerService) {

	get("/api/v1/passengers", (req, res) -> passengerService.getAllPassengers(), json());

	get("/api/v1/passengers/:id", (req, res) -> {
			String id = req.params(":id");
			Passenger passenger = passengerService.getOnePassenger(id);
			if (passenger != null) {
				// passenger.save();
				return passenger;
			}
			res.status(400);
			return new ResponseError("No passenger with id '%s' found", id);
		}, json());
}
