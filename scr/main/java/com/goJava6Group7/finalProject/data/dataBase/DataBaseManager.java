package scr.main.java.com.goJava6Group7.finalProject.data.dataBase;

import scr.main.java.com.goJava6Group7.finalProject.data.dao.impl.DaoHotel;
import scr.main.java.com.goJava6Group7.finalProject.data.dao.impl.DaoReservation;
import scr.main.java.com.goJava6Group7.finalProject.data.dao.impl.DaoRoom;
import scr.main.java.com.goJava6Group7.finalProject.data.dao.impl.DaoUser;
import scr.main.java.com.goJava6Group7.finalProject.data.dataBase.impl.DataBaseManagerFactory;
import scr.main.java.com.goJava6Group7.finalProject.data.dataBase.impl.DataBaseManagerXml;
import scr.main.java.com.goJava6Group7.finalProject.utils.IdUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Igor on 13.04.2017.
 */
public interface DataBaseManager {

    DaoHotel getDaoHotel();

    DaoReservation getDaoReservation();

    DaoRoom getDaoRoom();

    DaoUser getDaoUser();

    boolean initDB();

    boolean updateDatabase();

}
