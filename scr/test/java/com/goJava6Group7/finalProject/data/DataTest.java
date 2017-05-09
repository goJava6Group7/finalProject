package com.goJava6Group7.finalProject.data;

import com.goJava6Group7.finalProject.entities.*;
import org.junit.Test;
import com.goJava6Group7.finalProject.data.dataBase.DataBaseManager;
import com.goJava6Group7.finalProject.data.dataBase.impl.DataBaseManagerFactory;
import com.goJava6Group7.finalProject.data.dataBase.impl.DataBaseManagerXml;
import com.goJava6Group7.finalProject.data.dataBase.impl.DataBaseManagerBinary;
import com.goJava6Group7.finalProject.utils.IdUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Properties;
import java.time.LocalDate;

import static org.junit.Assert.assertEquals;


/**
 * Created by Igor on 13.04.2017.
 */
public class DataTest {

    @Test
    public void init(){
        DataBaseManager dataBaseManager = DataBaseManagerFactory.getInstance();
        IdUtil.getInstance(dataBaseManager);
        Hotel hotel = new Hotel("Live Hostel","Kiev", 2);
        Room room = new Room(5,5, RoomClass.Business, hotel.getId());
        User user = new User("John","Handsome","pswd");
        User user2 = new User("Jone","Handsome","pswd");
        LocalDate checkIn = LocalDate.now();
        LocalDate checkOut = LocalDate.now();
        Reservation reservation = new Reservation(user, room, checkIn, checkOut);
        switch (dataBaseManager.getClass().getSimpleName()){
            case "DataBaseManagerBinary" : {

                System.out.println(dataBaseManager.getClass().getSimpleName());

                try {

                    dataBaseManager.getDaoRoom().create(room);
                    dataBaseManager.getDaoHotel().create(hotel);
                    dataBaseManager.getDaoUser().create(user);
                    dataBaseManager.getDaoUser().create(user2);
                    dataBaseManager.getDaoReservation().create(reservation);

                    FileOutputStream fos = new FileOutputStream("Binary_Database.dat");
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(dataBaseManager);
                    oos.flush();
                    oos.close();
                    dataBaseManager = null;

                    FileInputStream fis = new FileInputStream("Binary_Database.dat");
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    dataBaseManager = (DataBaseManagerBinary) ois.readObject();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                break;

            }
            case "DataBaseManagerXml" :{

                System.out.println(dataBaseManager.getClass().getSimpleName());

                try {
                    Files.copy(FileSystems.getDefault().getPath("XML_Database")
                            , FileSystems.getDefault().getPath("XML_Database_Src"), StandardCopyOption.REPLACE_EXISTING);
                    dataBaseManager.getDaoRoom().create(room);
                    dataBaseManager.getDaoHotel().create(hotel);
                    dataBaseManager.getDaoUser().create(user);

                    File file = new File("XML_Database");
                    JAXBContext jaxbContext = JAXBContext.newInstance(DataBaseManagerXml.class);
                    Marshaller marshaller = jaxbContext.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    marshaller.marshal(dataBaseManager, file);
                    dataBaseManager = null;

                    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                    dataBaseManager = (DataBaseManagerXml) unmarshaller.unmarshal(file);
                    Files.copy(FileSystems.getDefault().getPath("XML_Database_Src")
                            , FileSystems.getDefault().getPath("XML_Database"), StandardCopyOption.REPLACE_EXISTING);
                    Files.deleteIfExists(FileSystems.getDefault().getPath("XML_Database_Src"));
                } catch (JAXBException | IOException e) {
                    e.printStackTrace();
                }
            }
        }


//        System.out.println(Arrays.deepToString(dataBaseManager.getDaoUser().getAll().toArray()));
//        System.out.println(Arrays.deepToString(dataBaseManager.getDaoReservation().getAll().toArray()));

        assertEquals(user, dataBaseManager.getDaoUser().get(user));
        assertEquals(hotel, dataBaseManager.getDaoHotel().get(hotel));
        assertEquals(room, dataBaseManager.getDaoRoom().get(room));

    }

    @Test
    public void dbCofig() {
        Properties propertiesTest = new Properties();
        Properties propertiesSrc = new Properties();
        propertiesTest.setProperty("dbType", "BINARY");
        try {
            propertiesSrc.load(Files.newInputStream(FileSystems.getDefault().getPath("application.properties")));
            propertiesTest.store(Files.newOutputStream(FileSystems.getDefault().getPath("application.properties")), "Test");
            assertEquals("DataBaseManagerBinary", DataBaseManagerFactory.getInstance().getClass().getSimpleName());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                propertiesSrc.store(Files.newOutputStream(FileSystems.getDefault().getPath("application.properties")), null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
