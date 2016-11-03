package app.domain;;

public abstract class Repositories {

    public static void initialise(Repositories instance) {
        Repositories.instance = instance;
    }

    public static CarRepository cars() {
        return instance.carsRepository();
    }

    protected abstract CarRepository carsRepository();

    public static DriverRepository drivers() {
        return instance.driversRepository();
    }

    protected abstract DriverRepository driversRepository();

    public static PassengerRepository passengers() {
        return instance.passengersRepository();
    }

    protected abstract PassengerRepository passengersRepository();

    public static RideRepository rides() {
        return instance.ridesRepository();
    }

    protected abstract RideRepository ridesRepository();

    private static Repositories instance;
}
