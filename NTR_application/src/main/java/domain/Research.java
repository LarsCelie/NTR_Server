package main.java.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "NTR_RESEARCH")
public class Research {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="research_id")
	@SequenceGenerator(name="research_id",sequenceName="NTR_RESEARCH_ID",allocationSize=1)
	private int id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "BEGINDATE")
	private Date beginDate;
	@Column(name = "ENDDATE")
	private Date endDate;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
