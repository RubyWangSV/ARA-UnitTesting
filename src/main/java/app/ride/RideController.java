package app.ride;

import spark.*;

public RideController (final RideService rideService) {

	get("/api/v1/rides", (req, res) -> rideService.getAllrides(), json());

	get("/api/v1/rides/:id", (req, res) -> {
			String id = req.params(":id");
			Ride ride = rideService.getOneride(id);
			if (ride != null) {
				// ride.save();
				return ride;
			}
			res.status(400);
			return new ResponseError("No ride with id '%s' found", id);
		}, json());
}
