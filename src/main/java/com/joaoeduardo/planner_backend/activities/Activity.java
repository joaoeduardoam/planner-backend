package com.joaoeduardo.planner_backend.activities;

import com.joaoeduardo.planner_backend.trip.Trip;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "activities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Activity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;


    @Column(name = "occurs_at", nullable = false)
    private LocalDateTime occursAt;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;


}
