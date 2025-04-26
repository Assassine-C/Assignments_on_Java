import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Anna Meshcheriakova
 * Class SmartHomeManagementSystem process commands from input and print successful output or message about mistake
 * in the command
 */

public class SmartHomeManagementSystem {
    /**
     * lights, cameras, and heaters contain objects of classes of devices
     */
    private static List<Light> lights = new ArrayList<>();
    private static List<Camera> cameras = new ArrayList<>();
    private static List<Heater> heaters = new ArrayList<>();
    private static final int NUMBER_OF_LIGHTS = 4;
    private static final int NUMBER_OF_CAMERAS = 2;
    private static final int NUMBER_OF_DEVICES = 10;
    private static final int INITIAL_ANGLE = 45;
    private static final int INITIAL_TEMPERATURE = 20;
    private static final int NUMBER_OF_FIELDS_COMMAND_1 = 3;
    private static final int NUMBER_OF_FIELDS_COMMAND_2 = 4;
    private static final int INDEX_OF_INT_VALUE_IN_COMMAND = 3;


    public static void main(String[] args) {
        for (int i = 0; i < NUMBER_OF_LIGHTS; i++) {
            lights.add(new Light(SmartDevice.Status.ON, false, Light.BrightnessLevel.LOW,
                    Light.LightColor.YELLOW));
            lights.get(i).setDeviceId(i);
        }
        for (int i = NUMBER_OF_LIGHTS; i < (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS); i++) {
            cameras.add(new Camera(SmartDevice.Status.ON, false, false, INITIAL_ANGLE));
            cameras.get(i - NUMBER_OF_LIGHTS).setDeviceId(i);
        }
        for (int i = (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS); i < NUMBER_OF_DEVICES; i++) {
            heaters.add(new Heater(SmartDevice.Status.ON, INITIAL_TEMPERATURE));
            heaters.get(i - (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS)).setDeviceId(i);
        }

        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        while (!command.equals("end")) {
            implementCommand(command);
            command = scanner.nextLine();
        }

    }

    /**
     * implementCommand take a String of command and call method of a particular command or print a mistake message
     * @param commandLine a String contains command
     */
    static void implementCommand(String commandLine) {
        String command = commandLine.split("\\s+")[0];
        switch (command) {
            case "DisplayAllStatus":
                displayStatus();
                break;
            case "TurnOn":
                turnOn(commandLine);
                break;
            case "TurnOff":
                turnOff(commandLine);
                break;
            case "StartCharging":
                startCharging(commandLine);
                break;
            case "StopCharging":
                stopCharging(commandLine);
                break;
            case "SetTemperature":
                setTemperature(commandLine);
                break;
            case "SetBrightness":
                setBrightness(commandLine);
                break;
            case "SetColor":
                setColor(commandLine);
                break;
            case "SetAngle":
                setAngle(commandLine);
                break;
            case "StartRecording":
                startRecording(commandLine);
                break;
            case "StopRecording":
                stopRecording(commandLine);
                break;
            default:
                System.out.println("Invalid command");
                break;
        }
    }

    /**
     * displayStatus prints statuses of all devices
     */
    static void displayStatus() {
        for (Light light : lights) {
            System.out.println(light.displayStatus());
        }
        for (Camera camera : cameras) {
            System.out.println(camera.displayStatus());
        }
        for (Heater heater : heaters) {
            System.out.println(heater.displayStatus());
        }
    }

    /**
     * method turnOn turns on a device if it is under conditions otherwise print error message
     * @param commandLine a String contains command
     */
    static void turnOn(String commandLine) {
        String[] fields = commandLine.split("\\s+");
        if (fields.length == NUMBER_OF_FIELDS_COMMAND_1 && isInteger(fields[2])) {
            int deviceId = Integer.parseInt(fields[2]);
            switch (fields[1]) {
                case "Light":
                    if (0 <= deviceId && deviceId <= (NUMBER_OF_LIGHTS - 1)) {
                        if (lights.get(deviceId).turnOn()) {
                            System.out.println("Light " + deviceId + " is on");
                        } else {
                            System.out.println("Light " + deviceId + " is already on");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                case "Camera":
                    if (NUMBER_OF_LIGHTS <= deviceId && deviceId <= (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS - 1)) {
                        if (cameras.get(deviceId - NUMBER_OF_LIGHTS).turnOn()) {
                            System.out.println("Camera " + deviceId + " is on");
                        } else {
                            System.out.println("Camera " + deviceId + " is already on");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                case "Heater":
                    if ((NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS) <= deviceId && deviceId <= (NUMBER_OF_DEVICES - 1)) {
                        if (heaters.get(deviceId - (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS)).turnOn()) {
                            System.out.println("Heater " + deviceId + " is on");
                        } else {
                            System.out.println("Heater " + deviceId + " is already on");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                default:
                    System.out.println("The smart device was not found");
                    break;
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    /**
     * method turnOff turns off a device if it is under conditions otherwise print error message
     * @param commandLine a String contains command
     */
    static void turnOff(String commandLine) {
        String[] fields = commandLine.split("\\s+");
        if (fields.length == NUMBER_OF_FIELDS_COMMAND_1 && isInteger(fields[2])) {
            int deviceId = Integer.parseInt(fields[2]);
            switch (fields[1]) {
                case "Light":
                    if (0 <= deviceId && deviceId <= (NUMBER_OF_LIGHTS - 1)) {
                        if (lights.get(deviceId).turnOff()) {
                            System.out.println("Light " + deviceId + " is off");
                        } else {
                            System.out.println("Light " + deviceId + " is already off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                case "Camera":
                    if (NUMBER_OF_LIGHTS <= deviceId && deviceId <= (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS - 1)) {
                        if (cameras.get(deviceId - NUMBER_OF_LIGHTS).turnOff()) {
                            System.out.println("Camera " + deviceId + " is off");
                        } else {
                            System.out.println("Camera " + deviceId + " is already off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                case "Heater":
                    if ((NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS) <= deviceId && deviceId <= (NUMBER_OF_DEVICES - 1)) {
                        if (heaters.get(deviceId - (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS)).turnOff()) {
                            System.out.println("Heater " + deviceId + " is off");
                        } else {
                            System.out.println("Heater " + deviceId + " is already off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                default:
                    System.out.println("The smart device was not found");
                    break;
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    /**
     * method startCharging starts charging of a device if it is under conditions otherwise print error message
     * @param commandLine a String contains command
     */
    static void startCharging(String commandLine) {
        String[] fields = commandLine.split("\\s+");
        if (fields.length == NUMBER_OF_FIELDS_COMMAND_1 && isInteger(fields[2])) {
            int deviceId = Integer.parseInt(fields[2]);
            switch (fields[1]) {
                case "Light":
                    if (0 <= deviceId && deviceId <= (NUMBER_OF_LIGHTS - 1)) {
                        if (lights.get(deviceId).startCharging()) {
                            System.out.println("Light " + deviceId + " is charging");
                        } else {
                            System.out.println("Light " + deviceId + " is already charging");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                case "Camera":
                    if (NUMBER_OF_LIGHTS <= deviceId && deviceId <= (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS - 1)) {
                        if (cameras.get(deviceId - NUMBER_OF_LIGHTS).startCharging()) {
                            System.out.println("Camera " + deviceId + " is charging");
                        } else {
                            System.out.println("Camera " + deviceId + " is already charging");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                case "Heater":
                    if ((NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS) <= deviceId && deviceId <= (NUMBER_OF_DEVICES - 1)) {
                        System.out.println("Heater " + deviceId + " is not chargeable");
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                default:
                    System.out.println("The smart device was not found");
                    break;
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    /**
     * method stopCharging stops charging of a device if it is under conditions otherwise print error message
     * @param commandLine a String contains command
     */
    static void stopCharging(String commandLine) {
        String[] fields = commandLine.split("\\s+");
        if (fields.length == NUMBER_OF_FIELDS_COMMAND_1 && isInteger(fields[2])) {
            int deviceId = Integer.parseInt(fields[2]);
            switch (fields[1]) {
                case "Light":
                    if (0 <= deviceId && deviceId <= (NUMBER_OF_LIGHTS - 1)) {
                        if (lights.get(deviceId).stopCharging()) {
                            System.out.println("Light " + deviceId + " stopped charging");
                        } else {
                            System.out.println("Light " + deviceId + " is not charging");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                case "Camera":
                    if (NUMBER_OF_LIGHTS <= deviceId && deviceId <= (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS - 1)) {
                        if (cameras.get(deviceId - NUMBER_OF_LIGHTS).stopCharging()) {
                            System.out.println("Camera " + deviceId + " stopped charging");
                        } else {
                            System.out.println("Camera " + deviceId + " is not charging");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                case "Heater":
                    if ((NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS) <= deviceId && deviceId <= (NUMBER_OF_DEVICES - 1)) {
                        System.out.println("Heater " + deviceId + " is not chargeable");
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                default:
                    System.out.println("The smart device was not found");
                    break;
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    /**
     * method setTemperature sets temperature of a heater if it is under conditions otherwise print error message
     * @param commandLine a String contains command
     */
    static void setTemperature(String commandLine) {
        String[] fields = commandLine.split("\\s+");
        if (fields.length == NUMBER_OF_FIELDS_COMMAND_2 && isInteger(fields[2])
                && isInteger(fields[INDEX_OF_INT_VALUE_IN_COMMAND])) {
            int deviceId = Integer.parseInt(fields[2]);
            int temperature = Integer.parseInt(fields[INDEX_OF_INT_VALUE_IN_COMMAND]);
            switch (fields[1]) {
                case "Light":
                    if (0 <= deviceId && deviceId <= (NUMBER_OF_LIGHTS - 1)) {
                        if (lights.get(deviceId).isOn()) {
                            System.out.println("Light " + deviceId + " is not a heater");
                        } else {
                            System.out.println("You can't change the status of the Light " + deviceId
                                    + " while it is off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                case "Camera":
                    if (NUMBER_OF_LIGHTS <= deviceId && deviceId <= (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS - 1)) {
                        if (cameras.get(deviceId - NUMBER_OF_LIGHTS).isOn()) {
                            System.out.println("Camera " + deviceId + " is not a heater");
                        } else {
                            System.out.println("You can't change the status of the Camera " + deviceId
                                    + " while it is off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                case "Heater":
                    if ((NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS) <= deviceId && deviceId <= (NUMBER_OF_DEVICES - 1)) {
                        if (heaters.get(deviceId - (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS)).isOn()) {
                            if (heaters.get(deviceId
                                    - (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS)).setTemperature(temperature)) {
                                System.out.println("Heater " + deviceId + " temperature is set to " + temperature);
                            } else {
                                System.out.println("Heater " + deviceId
                                        + " temperature should be in the range [15, 30]");
                            }
                        } else {
                            System.out.println("You can't change the status of the Heater " + deviceId
                                    + " while it is off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                default:
                    System.out.println("The smart device was not found");
                    break;
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    /**
     * method setBrightness sets brightness level of a light if it is under conditions otherwise print error message
     * @param commandLine a String contains command
     */
    static void setBrightness(String commandLine) {
        String[] fields = commandLine.split("\\s+");
        if (fields.length == NUMBER_OF_FIELDS_COMMAND_2 && isInteger(fields[2])) {
            int deviceId = Integer.parseInt(fields[2]);
            String brightnessLevel = fields[INDEX_OF_INT_VALUE_IN_COMMAND];
            switch (fields[1]) {
                case "Light":
                    if (0 <= deviceId && deviceId <= (NUMBER_OF_LIGHTS - 1)) {
                        if (lights.get(deviceId).isOn()) {
                            switch (brightnessLevel) {
                                case "LOW":
                                    lights.get(deviceId).setBrightnessLevel(Light.BrightnessLevel.LOW);
                                    System.out.println("Light " + deviceId + " brightness level is set to LOW");
                                    break;
                                case "MEDIUM":
                                    lights.get(deviceId).setBrightnessLevel(Light.BrightnessLevel.MEDIUM);
                                    System.out.println("Light " + deviceId + " brightness level is set to MEDIUM");
                                    break;
                                case "HIGH":
                                    lights.get(deviceId).setBrightnessLevel(Light.BrightnessLevel.HIGH);
                                    System.out.println("Light " + deviceId + " brightness level is set to HIGH");
                                    break;
                                default:
                                    System.out.println("The brightness can only be one of \"LOW\", \"MEDIUM\", or"
                                            + " \"HIGH\"");
                            }
                        } else {
                            System.out.println("You can't change the status of the Light " + deviceId
                                    + " while it is off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                case "Camera":
                    if (NUMBER_OF_LIGHTS <= deviceId && deviceId <= (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS - 1)) {
                        if (cameras.get(deviceId - NUMBER_OF_LIGHTS).isOn()) {
                            System.out.println("Camera " + deviceId + " is not a light");
                        } else {
                            System.out.println("You can't change the status of the Camera " + deviceId
                                    + " while it is off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                case "Heater":
                    if ((NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS) <= deviceId && deviceId <= (NUMBER_OF_DEVICES - 1)) {
                        if (heaters.get(deviceId - (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS)).isOn()) {
                            System.out.println("Heater " + deviceId + " is not a light");
                        } else {
                            System.out.println("You can't change the status of the Heater " + deviceId
                                    + " while it is off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                default:
                    System.out.println("The smart device was not found");
                    break;
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    /**
     * method setColor sets color of a light if it is under conditions otherwise print error message
     * @param commandLine a String contains command
     */
    static void setColor(String commandLine) {
        String[] fields = commandLine.split("\\s+");
        if (fields.length == NUMBER_OF_FIELDS_COMMAND_2 && isInteger(fields[2])) {
            int deviceId = Integer.parseInt(fields[2]);
            String color = fields[INDEX_OF_INT_VALUE_IN_COMMAND];
            switch (fields[1]) {
                case "Light":
                    if (0 <= deviceId && deviceId <= (NUMBER_OF_LIGHTS - 1)) {
                        if (lights.get(deviceId).isOn()) {
                            switch (color) {
                                case "YELLOW":
                                    lights.get(deviceId).setLightColor(Light.LightColor.YELLOW);
                                    System.out.println("Light " + deviceId + " color is set to YELLOW");
                                    break;
                                case "WHITE":
                                    lights.get(deviceId).setLightColor(Light.LightColor.WHITE);
                                    System.out.println("Light " + deviceId + " color is set to WHITE");
                                    break;
                                default:
                                    System.out.println("The light color can only be \"YELLOW\" or \"WHITE\"");
                            }
                        } else {
                            System.out.println("You can't change the status of the Light " + deviceId
                                    + " while it is off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                case "Camera":
                    if (NUMBER_OF_LIGHTS <= deviceId && deviceId <= (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS - 1)) {
                        if (cameras.get(deviceId - NUMBER_OF_LIGHTS).isOn()) {
                            System.out.println("Camera " + deviceId + " is not a light");
                        } else {
                            System.out.println("You can't change the status of the Camera " + deviceId
                                    + " while it is off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                case "Heater":
                    if ((NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS) <= deviceId && deviceId <= (NUMBER_OF_DEVICES - 1)) {
                        if (heaters.get(deviceId - (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS)).isOn()) {
                            System.out.println("Heater " + deviceId + " is not a light");
                        } else {
                            System.out.println("You can't change the status of the Heater " + deviceId
                                    + " while it is off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                default:
                    System.out.println("The smart device was not found");
                    break;
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    /**
     * method setAngle sets angle of a camera if it is under conditions otherwise print error message
     * @param commandLine a String contains command
     */
    static void setAngle(String commandLine) {
        String[] fields = commandLine.split("\\s+");
        if (fields.length == NUMBER_OF_FIELDS_COMMAND_2 && isInteger(fields[2])
                && isInteger(fields[INDEX_OF_INT_VALUE_IN_COMMAND])) {
            int deviceId = Integer.parseInt(fields[2]);
            int angle = Integer.parseInt(fields[INDEX_OF_INT_VALUE_IN_COMMAND]);
            switch (fields[1]) {
                case "Light":
                    if (0 <= deviceId && deviceId <= (NUMBER_OF_LIGHTS - 1)) {
                        if (lights.get(deviceId).isOn()) {
                            System.out.println("Light " + deviceId + " is not a camera");
                        } else {
                            System.out.println("You can't change the status of the Light " + deviceId
                                    + " while it is off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                case "Camera":
                    if (NUMBER_OF_LIGHTS <= deviceId && deviceId <= (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS - 1)) {
                        if (cameras.get(deviceId - NUMBER_OF_LIGHTS).isOn()) {
                            if (cameras.get(deviceId - NUMBER_OF_LIGHTS).setCameraAngle(angle)) {
                                System.out.println("Camera " + deviceId + " angle is set to " + angle);
                            } else {
                                System.out.println("Camera " + deviceId + " angle should be in the range [-60, 60]");
                            }
                        } else {
                            System.out.println("You can't change the status of the Camera " + deviceId
                                    + " while it is off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                case "Heater":
                    if ((NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS) <= deviceId && deviceId <= (NUMBER_OF_DEVICES - 1)) {
                        if (heaters.get(deviceId - (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS)).isOn()) {
                            System.out.println("Heater " + deviceId + " is not a camera");
                        } else {
                            System.out.println("You can't change the status of the Heater " + deviceId
                                    + " while it is off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                default:
                    System.out.println("The smart device was not found");
                    break;
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    /**
     * method startRecording starts recording of a camera if it is under conditions otherwise print error message
     * @param commandLine a String contains command
     */
    static void startRecording(String commandLine) {
        String[] fields = commandLine.split("\\s+");
        if (fields.length == NUMBER_OF_FIELDS_COMMAND_1 && isInteger(fields[2])) {
            int deviceId = Integer.parseInt(fields[2]);
            switch (fields[1]) {
                case "Light":
                    if (0 <= deviceId && deviceId <= (NUMBER_OF_LIGHTS - 1)) {
                        if (lights.get(deviceId).isOn()) {
                            System.out.println("Light " + deviceId + " is not a camera");
                        } else {
                            System.out.println("You can't change the status of the Light " + deviceId
                                    + " while it is off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                case "Camera":
                    if (NUMBER_OF_LIGHTS <= deviceId && deviceId <= (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS - 1)) {
                        if (cameras.get(deviceId - NUMBER_OF_LIGHTS).isOn()) {
                            if (cameras.get(deviceId - NUMBER_OF_LIGHTS).startRecording()) {
                                System.out.println("Camera " + deviceId + " started recording");
                            } else {
                                System.out.println("Camera " + deviceId + " is already recording");
                            }
                        } else {
                            System.out.println("You can't change the status of the Camera " + deviceId
                                    + " while it is off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                case "Heater":
                    if ((NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS) <= deviceId && deviceId <= (NUMBER_OF_DEVICES - 1)) {
                        if (heaters.get(deviceId - (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS)).isOn()) {
                            System.out.println("Heater " + deviceId + " is not a camera");
                        } else {
                            System.out.println("You can't change the status of the Heater " + deviceId
                                    + " while it is off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                default:
                    System.out.println("The smart device was not found");
                    break;
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    /**
     * method stopRecording stops recording of a camera if it is under conditions otherwise print error message
     * @param commandLine a String contains command
     */
    static void stopRecording(String commandLine) {
        String[] fields = commandLine.split("\\s+");
        if (fields.length == NUMBER_OF_FIELDS_COMMAND_1 && isInteger(fields[2])) {
            int deviceId = Integer.parseInt(fields[2]);
            switch (fields[1]) {
                case "Light":
                    if (0 <= deviceId && deviceId <= (NUMBER_OF_LIGHTS - 1)) {
                        if (lights.get(deviceId).isOn()) {
                            System.out.println("Light " + deviceId + " is not a camera");
                        } else {
                            System.out.println("You can't change the status of the Light " + deviceId
                                    + " while it is off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                case "Camera":
                    if (NUMBER_OF_LIGHTS <= deviceId && deviceId <= (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS - 1)) {
                        if (cameras.get(deviceId - NUMBER_OF_LIGHTS).isOn()) {
                            if (cameras.get(deviceId - NUMBER_OF_LIGHTS).stopRecording()) {
                                System.out.println("Camera " + deviceId + " stopped recording");
                            } else {
                                System.out.println("Camera " + deviceId + " is not recording");
                            }
                        } else {
                            System.out.println("You can't change the status of the Camera " + deviceId
                                    + " while it is off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                case "Heater":
                    if ((NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS) <= deviceId && deviceId <= (NUMBER_OF_DEVICES - 1)) {
                        if (heaters.get(deviceId - (NUMBER_OF_LIGHTS + NUMBER_OF_CAMERAS)).isOn()) {
                            System.out.println("Heater " + deviceId + " is not a camera");
                        } else {
                            System.out.println("You can't change the status of the Heater " + deviceId
                                    + " while it is off");
                        }
                    } else {
                        System.out.println("The smart device was not found");
                    }
                    break;
                default:
                    System.out.println("The smart device was not found");
                    break;
            }
        } else {
            System.out.println("Invalid command");
        }
    }

    /**
     * method isInteger checks if string contains integer or not
     * @param string string that is needed to be checked
     * @return true if string contain integer and return false in all other cases
     */
    public static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true; // It's an integer
        } catch (NumberFormatException e) {
            return false; // Not an integer
        }
    }
}

interface Controllable {
    boolean turnOff();
    boolean turnOn();
    boolean isOn();
}

abstract class SmartDevice implements Controllable {
    enum Status {
        OFF("OFF"),
        ON("ON");
        private String status;
        Status(String status) {
            this.status = status;
        }
        public String getStatus() {
            return status;
        }
    }

    private Status status;
    private int deviceId;
    private static int numberOfDevices;

    public SmartDevice(Status status) {
        this.status = status;
    }

    public String displayStatus() {
        return status.toString();
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean turnOff() {
        if (status == Status.ON) {
            status = Status.OFF;
            return true;
        }
        return false;
    }

    @Override
    public boolean turnOn() {
        if (status == Status.OFF) {
            status = Status.ON;
            return true;
        }
        return false;
    }

    @Override
    public boolean isOn() {
        return (status == Status.ON);
    }

    public boolean checkStatusAccess() {
        return (status == Status.ON);
    }

}

class Heater extends SmartDevice {
    private int temperature;
    static final int MAX_HEATER_TEMPERATURE = 30;
    static final int MIN_HEATER_TEMPERATURE = 15;

    public Heater(Status status, int temperature) {
        super(status);
        this.temperature = temperature;
    }

    public int getTemperature() {
        return temperature;
    }

    public boolean setTemperature(int temperature) {
        if ((temperature >= MIN_HEATER_TEMPERATURE)
                && (temperature <= MAX_HEATER_TEMPERATURE)) {
            this.temperature = temperature;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String displayStatus() {
        return "Heater " + getDeviceId() + " is " + getStatus() + " and the temperature is " + temperature + ".";
    }
}

interface Chargeable {
    boolean isCharging();
    boolean startCharging();
    boolean stopCharging();
}

class Camera extends SmartDevice implements Chargeable {
    static final int MAX_CAMERA_ANGLE = 60;
    static final int MIN_CAMERA_ANGLE = -60;
    private boolean charging;
    private boolean recording;
    private int angle;

    public Camera(Status status, boolean charging, boolean recording, int angle) {
        super(status);
        this.charging = charging;
        this.recording = recording;
        this.angle = angle;
    }

    public int getAngle() {
        return angle;
    }

    public boolean setCameraAngle(int angle) {
        if ((angle >= MIN_CAMERA_ANGLE) && (angle <= MAX_CAMERA_ANGLE)) {
            this.angle = angle;
            return true;
        } else {
            return false;
        }
    }

    public boolean startRecording() {
        if (recording) {
            return false;
        }
        recording = true;
        return true;
    }

    public boolean stopRecording() {
        if (recording) {
            recording = false;
            return true;
        }
        return false;
    }

    public boolean isRecording() {
        return recording;
    }

    @Override
    public boolean isCharging() {
        return charging;
    }

    @Override
    public boolean startCharging() {
        if (charging) {
            return false;
        }
        charging = true;
        return true;
    }

    @Override
    public boolean stopCharging() {
        if (charging) {
            charging = false;
            return true;
        }
        return false;
    }

    @Override
    public String displayStatus() {
        return "Camera " + getDeviceId() + " is " + getStatus() + ", the angle is "
                + angle + ", the charging status is " + charging + ", and the recording status is " + recording + '.';
    }
}

class Light extends SmartDevice implements Chargeable {
    enum LightColor {
        WHITE("WHITE"), YELLOW("YELLOW");
        private String lightColor;
        LightColor(String lightColor) {
            this.lightColor = lightColor;
        }
        public String getColor() {
            return lightColor;
        }
    }

    enum BrightnessLevel {
        HIGH("HIGH"), MEDIUM("MEDIUM"), LOW("LOW");
        private String brightnessLevel;
        BrightnessLevel(String brightnessLevel) {
            this.brightnessLevel = brightnessLevel;
        }
        public String getBrightnessLevel() {
            return brightnessLevel;
        }
    }

    private boolean charging;
    private BrightnessLevel brightnessLevel;
    private LightColor lightColor;

    public Light(Status status, boolean charging, BrightnessLevel brightnessLevel, LightColor lightColor) {
        super(status);
        this.charging = charging;
        this.brightnessLevel = brightnessLevel;
        this.lightColor = lightColor;
    }

    public LightColor getLightColor() {
        return lightColor;
    }

    public void setLightColor(LightColor lightColor) {
        this.lightColor = lightColor;
    }

    public BrightnessLevel getBrightnessLevel() {
        return brightnessLevel;
    }

    public void setBrightnessLevel(BrightnessLevel brightnessLevel) {
        this.brightnessLevel = brightnessLevel;
    }

    @Override
    public boolean isCharging() {
        return charging;
    }

    @Override
    public boolean startCharging() {
        if (charging) {
            return false;
        }
        charging = true;
        return true;
    }

    @Override
    public boolean stopCharging() {
        if (charging) {
            charging = false;
            return true;
        }
        return false;
    }

    @Override
    public String displayStatus() {
        return "Light " + getDeviceId() + " is " + getStatus() + ", the color is "
                + lightColor + ", the charging status is " + charging
                + ", and the brightness level is " + brightnessLevel + ".";
    }
}