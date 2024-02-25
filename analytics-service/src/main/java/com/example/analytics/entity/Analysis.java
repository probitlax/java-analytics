package com.example.analytics.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ANALYSIS")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Analysis implements Serializable {
    private static final long serialVersionUID = 1L;

    @Version
    private Long version;
    @Id
    @Column(name = "ANL_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "ANALYSIS_TYPE")
    @Enumerated(EnumType.STRING)
    private AnalysisType analysisType;
    @ManyToOne
    @JoinColumn(name = "OWNER_ID", nullable = false)
    @EqualsAndHashCode.Exclude
    private Profile owner;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ANALYSIS_VIEWER",
            joinColumns = @JoinColumn(name = "ANALYSIS_ID", referencedColumnName = "ANL_ID"),
            inverseJoinColumns = @JoinColumn(name = "PROFILE_ID", referencedColumnName = "PRF_ID"))
    @EqualsAndHashCode.Exclude
    private Set<Profile> viewers = new HashSet<>();
    @Column(name = "HIDDEN_INFO")
    private String hiddenInfo;


    public Analysis(Integer id, AnalysisType analysisType, Profile owner, Set<Profile> viewers) {
        this.id = id;
        this.analysisType = analysisType;
        this.owner = owner;
        this.viewers = viewers;
    }
}
