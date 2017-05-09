/**
 * Created by guillaume on 5/9/17.
 */

@XmlJavaTypeAdapters({
        @XmlJavaTypeAdapter(type=LocalDate.class,
                value=LocalDateAdapter.class),
})

package com.goJava6Group7.finalProject;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.time.LocalDate;