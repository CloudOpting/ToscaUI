package eu.cloudopting.ui.ToscaUI.server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationList {

	private List<Content> content = new ArrayList<Content>();
	private Integer totalPages;
	private Boolean last;
	private Integer totalElements;
	private Integer size;
	private Integer number;
	private List<Sort> sort = new ArrayList<Sort>();
	private Boolean first;
	private Integer numberOfElements;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The content
	 */
	public List<Content> getContent() {
		return content;
	}

	/**
	 * 
	 * @param content
	 * The content
	 */
	public void setContent(List<Content> content) {
		this.content = content;
	}

	/**
	 * 
	 * @return
	 * The totalPages
	 */
	public Integer getTotalPages() {
		return totalPages;
	}

	/**
	 * 
	 * @param totalPages
	 * The totalPages
	 */
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * 
	 * @return
	 * The last
	 */
	public Boolean getLast() {
		return last;
	}

	/**
	 * 
	 * @param last
	 * The last
	 */
	public void setLast(Boolean last) {
		this.last = last;
	}

	/**
	 * 
	 * @return
	 * The totalElements
	 */
	public Integer getTotalElements() {
		return totalElements;
	}

	/**
	 * 
	 * @param totalElements
	 * The totalElements
	 */
	public void setTotalElements(Integer totalElements) {
		this.totalElements = totalElements;
	}

	/**
	 * 
	 * @return
	 * The size
	 */
	public Integer getSize() {
		return size;
	}

	/**
	 * 
	 * @param size
	 * The size
	 */
	public void setSize(Integer size) {
		this.size = size;
	}

	/**
	 * 
	 * @return
	 * The number
	 */
	public Integer getNumber() {
		return number;
	}

	/**
	 * 
	 * @param number
	 * The number
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}

	/**
	 * 
	 * @return
	 * The sort
	 */
	public List<Sort> getSort() {
		return sort;
	}

	/**
	 * 
	 * @param sort
	 * The sort
	 */
	public void setSort(List<Sort> sort) {
		this.sort = sort;
	}

	/**
	 * 
	 * @return
	 * The first
	 */
	public Boolean getFirst() {
		return first;
	}

	/**
	 * 
	 * @param first
	 * The first
	 */
	public void setFirst(Boolean first) {
		this.first = first;
	}

	/**
	 * 
	 * @return
	 * The numberOfElements
	 */
	public Integer getNumberOfElements() {
		return numberOfElements;
	}

	/**
	 * 
	 * @param numberOfElements
	 * The numberOfElements
	 */
	public void setNumberOfElements(Integer numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
