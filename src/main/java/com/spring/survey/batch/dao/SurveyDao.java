package com.spring.survey.batch.dao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SURVEY")
public class SurveyDao implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer year;
	private String insdustryAgg;
	private String industryName;
	private String industryCode;
	private String unit;
	private String variableCode;
	private String variableName;
	private String variableCategory;
	private String value; 
	private String industryAgg2;
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getInsdustryAgg() {
		return insdustryAgg;
	}
	public void setInsdustryAgg(String insdustryAgg) {
		this.insdustryAgg = insdustryAgg;
	}
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIndustryCode() {
		return industryCode;
	}
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getVariableCode() {
		return variableCode;
	}
	public void setVariableCode(String variableCode) {
		this.variableCode = variableCode;
	}
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	public String getVariableCategory() {
		return variableCategory;
	}
	public void setVariableCategory(String variableCategory) {
		this.variableCategory = variableCategory;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getIndustryAgg2() {
		return industryAgg2;
	}
	public void setIndustryAgg2(String industryAgg2) {
		this.industryAgg2 = industryAgg2;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "SurveyDao [year=" + year + ", insdustryAgg=" + insdustryAgg + ", industryName=" + industryName
				+ ", industryCode=" + industryCode + ", unit=" + unit + ", variableCode=" + variableCode
				+ ", variableName=" + variableName + ", variableCategory=" + variableCategory + ", value=" + value
				+ ", industryAgg2=" + industryAgg2 + "]";
	}
}
