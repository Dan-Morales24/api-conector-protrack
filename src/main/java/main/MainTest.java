package main;


import java.util.List;
import java.util.stream.Collectors;

import com.ia.conectors.model.DeviceDTO;
import com.ia.conectors.model.VehicleDTO;
import com.ia.conectors.service.AccesTokenService;
import com.ia.conectors.service.GetDeviceService;
import com.ia.conectors.service.GetLocationService;
import com.ia.conectors.utils.DeviceFinder;

public class MainTest {

    public static void main(String[] args) {
        try {
            // 1. Servicios
            AccesTokenService client = new AccesTokenService();
            GetLocationService location = new GetLocationService();
            GetDeviceService deviceService = new GetDeviceService();
            DeviceFinder finder = new DeviceFinder();

            // 2. Credenciales
            String account = "Lucianomorales2022";
            String password = "888888";

            // lista de nombres que quieres buscar
            List<String> nombresBuscados = List.of("VT05S-01234", "VT05S-23456");

            // 3. Obtener token
            String token = client.obtenerToken(account, password);
            System.out.println("Token obtenido: " + token);

            // 4. Obtener lista de dispositivos del cliente
            List<DeviceDTO> devices = deviceService.obtenerDispositivos(token);

            // 5. Buscar dispositivos por los nombres que pasaste
            List<DeviceDTO> encontrados = finder.buscarPorNombres(devices, nombresBuscados);

            if (encontrados.isEmpty()) {
                System.out.println("No se encontró ningún dispositivo con esos nombres: " + nombresBuscados);
                return;
            }

            // 6. Extraer sus IMEIs
            List<String> imeis = encontrados.stream()
                    .map(DeviceDTO::getImei)
                    .collect(Collectors.toList());

            System.out.println("IMEIs encontrados: " + imeis);

            // 7. Obtener ubicaciones con esos IMEIs
            List<VehicleDTO> vehiculos = location.getLocation(token, imeis);

            // 8. Mostrar resultados
            System.out.println("Ubicaciones obtenidas:");
            for (VehicleDTO v : vehiculos) {
                System.out.printf("IMEI: %s | Lat: %f | Lon: %f | Speed: %.2f%n",
                        v.getImei(), v.getLatitude(), v.getLongitude(), v.getSpeed());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}