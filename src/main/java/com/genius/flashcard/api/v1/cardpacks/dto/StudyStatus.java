package com.genius.flashcard.api.v1.cardpacks.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 학습상태
 *
 * @author 박재익
 *
 */
@Entity
@Table(name = "STUDY_STATUS", indexes = @Index(columnList = "USER_ID") )
public class StudyStatus implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3727135471879053557L;

	@EmbeddedId
	StudyStatus.PK pk;

//	@Column(name = "USER_ID")
//	String userId;
//
//	@Column(name = "CARDPACK_ID")
//	String cardpackId;

	/**
	 * S3 키
	 */
	@Column(name = "S3_KEY")
	String s3Key;

	public StudyStatus.PK getPk() {
		return pk;
	}

	public void setPk(StudyStatus.PK pk) {
		this.pk = pk;
	}

	public String getS3Key() {
		return s3Key;
	}

	public void setS3Key(String s3Key) {
		this.s3Key = s3Key;
	}

	@Embeddable
	public static class PK implements Serializable {
		/**
		*
		*/
		private static final long serialVersionUID = 2402120266617286015L;

		@Column(name = "USER_ID")
		private String userId;

		@Column(name = "CARDPACK_ID")
		private String cardpackId;

		public PK() {
			super();
		}

		public PK(String userId, String cardpackId) {
			super();
			this.userId = userId;
			this.cardpackId = cardpackId;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getCardpackId() {
			return cardpackId;
		}

		public void setCardpackId(String cardpackId) {
			this.cardpackId = cardpackId;
		}

		@Override
		public boolean equals(Object o) {
			if (o == this) {
				return true;
			}
			if (!(o instanceof PK)) {
				return false;
			}
			PK other = (PK) o;
			return this.userId.equals(other.userId) && this.cardpackId.equals(other.cardpackId);
		}

		@Override
		public int hashCode() {
			return this.userId.hashCode() ^ this.cardpackId.hashCode();
		}

	}
}
