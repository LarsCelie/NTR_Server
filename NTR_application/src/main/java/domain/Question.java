package main.java.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "NTR_QUESTION")
public class Question {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="question_id")
	@SequenceGenerator(name="question_id",sequenceName="NTR_QUESTION_ID",allocationSize=1)
    private int id;
	@Column(name = "DESCRIPTION")
    private String description;
	@Column(name = "SEQUENCE")
    private int sequence;
	@Column(name = "TYPE")
    private String type;
	@ManyToOne
	@JoinColumn(name = "SURVEYID")
	private Survey survey;
	@OneToMany(mappedBy="question")
    private List<Option> options;
	@OneToMany(mappedBy="question")
    private List<Attachment> attachments;

    public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
