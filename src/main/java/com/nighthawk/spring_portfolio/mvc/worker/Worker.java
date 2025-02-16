package com.nighthawk.spring_portfolio.mvc.worker;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Convert;
import static jakarta.persistence.FetchType.EAGER;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;

import com.vladmihalcea.hibernate.type.json.JsonType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Convert(attributeName = "worker", converter = JsonType.class)
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Size(min = 5)
    @Column(unique = true)
    @Email
    private String email;

    @NotEmpty
    private String password;

    @NonNull
    @Size(min = 2, max = 30, message = "Name (2 to 30 chars)")
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @ManyToMany(fetch = EAGER)
    private Collection<WorkerRole> roles = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Map<String, Object>> stats = new HashMap<>();

    public Worker(String email, String password, String name, Date dob) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.dob = dob;
    }

    public int getAge() {
        if (this.dob != null) {
            LocalDate birthDay = this.dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return Period.between(birthDay, LocalDate.now()).getYears();
        }
        return -1;
    }

    public static Worker[] init() {
        Worker w1 = new Worker();
        w1.setName("John Doe");
        w1.setEmail("john.doe@example.com");
        w1.setPassword("workerPassword");
        try {
            Date d = new SimpleDateFormat("MM-dd-yyyy").parse("01-01-1980");
            w1.setDob(d);
        } catch (Exception e) {
        }

        Worker w2 = new Worker();
        w2.setName("Jane Smith");
        w2.setEmail("jane.smith@example.com");
        w2.setPassword("workerPassword");
        try {
            Date d = new SimpleDateFormat("MM-dd-yyyy").parse("01-01-1985");
            w2.setDob(d);
        } catch (Exception e) {
        }

        Worker w3 = new Worker();
        w3.setName("Bob Johnson");
        w3.setEmail("bob.johnson@example.com");
        w3.setPassword("workerPassword");
        try {
            Date d = new SimpleDateFormat("MM-dd-yyyy").parse("01-01-1990");
            w3.setDob(d);
        } catch (Exception e) {
        }

        Worker workers[] = { w1, w2, w3 };
        for (Worker worker : workers) {
            Map<String, Object> statsMap = new HashMap<>();
            statsMap.put("hoursWorked", 40);
            statsMap.put("projectsCompleted", 5);
            worker.getStats().put("2022-12-18", statsMap);
        }
        return workers;
    }

    public static void main(String[] args) {
        Worker workers[] = init();
        for (Worker worker : workers) {
            System.out.println(worker);
        }
    }
}