package org.acme.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task {

    public Task(){
        
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private boolean isCompleted;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SubTask> subTasks = new HashSet<>();
    
    public void addChild(SubTask child) {
        subTasks.add(child);
    }

    public void removeChild(SubTask child) {
        subTasks.remove(child);
    }

    public List<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks); 
    }
}
