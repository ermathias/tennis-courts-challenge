package com.tenniscourts.tenniscourts;

import com.tenniscourts.config.persistence.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class TennisCourt extends BaseEntity<Long> {

    @Column
    @NotNull
    private String name;
}
