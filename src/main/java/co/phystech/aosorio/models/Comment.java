/**
 * 
 */
package co.phystech.aosorio.models;

import java.util.Date;
import java.util.UUID;

import co.phystech.aosorio.config.Constants;

/**
 * @author AOSORIO
 *
 */
public class Comment {
	
	UUID comment_uuid;
	UUID book_uuid;
	private String author;
	private String aboutAuthor;
	private String aboutGenre;
	private String aboutCadre;
	private String aboutCharacters;
	
	private String resume;
	private String extrait;
	private String appreciation;
	
	private boolean isCompleted;
	private Date submission_date;
	private Date completion_date;

	public UUID getComment_uuid() {
		return comment_uuid;
	}

	public void setComment_uuid(UUID comment_uuid) {
		this.comment_uuid = comment_uuid;
	}

	public UUID getBook_uuid() {
		return book_uuid;
	}

	public void setBook_uuid(UUID book_uuid) {
		this.book_uuid = book_uuid;
	}

	public String getAuthor() {
		if( author.isEmpty()) return Constants.EMPTY_FIELD;
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAboutAuthor() {
		if( aboutAuthor.isEmpty()) return Constants.EMPTY_FIELD;
		return aboutAuthor;
	}

	public void setAboutAuthor(String aboutAuthor) {
		this.aboutAuthor = aboutAuthor;
	}

	public String getAboutGenre() {
		if( aboutGenre.isEmpty()) return Constants.EMPTY_FIELD;
		return aboutGenre;
	}

	public void setAboutGenre(String aboutGenre) {
		this.aboutGenre = aboutGenre;
	}

	public String getAboutCadre() {
		if( aboutCadre.isEmpty()) return Constants.EMPTY_FIELD;
		return aboutCadre;
	}

	public void setAboutCadre(String aboutCadre) {
		this.aboutCadre = aboutCadre;
	}

	public String getAboutCharacters() {
		if( aboutCharacters.isEmpty()) return Constants.EMPTY_FIELD;
		return aboutCharacters;
	}

	public void setAboutCharacters(String aboutCharacters) {
		this.aboutCharacters = aboutCharacters;
	}

	public String getResume() {
		if( resume.isEmpty()) return Constants.EMPTY_FIELD;
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getExtrait() {
		if( extrait.isEmpty()) return Constants.EMPTY_FIELD;
		return extrait;
	}

	public void setExtrait(String extrait) {
		this.extrait = extrait;
	}

	public String getAppreciation() {
		if( appreciation.isEmpty()) return Constants.EMPTY_FIELD;
		return appreciation;
	}

	public void setAppreciation(String appreciation) {
		this.appreciation = appreciation;
	}

	public Date getSubmission_date() {
		return submission_date;
	}

	public void setSubmission_date(Date submission_date) {
		this.submission_date = submission_date;
	}

	/**
	 * @return the isCompleted
	 */
	public boolean getIsCompleted() {
		return isCompleted;
	}

	/**
	 * @param isCompleted the isCompleted to set
	 */
	public void setIsCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	/**
	 * @return the completion_date
	 */
	public Date getCompletion_date() {
		return completion_date;
	}

	/**
	 * @param completion_date the completion_date to set
	 */
	public void setCompletion_date(Date completion_date) {
		this.completion_date = completion_date;
	}
	
	
}
