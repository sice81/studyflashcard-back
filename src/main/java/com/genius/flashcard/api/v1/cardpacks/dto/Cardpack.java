	package com.genius.flashcard.api.v1.cardpacks.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.genius.flashcard.common.enums.CardpackAccessCdEnum;
import com.genius.flashcard.common.enums.CardpackStatusCdEnum;

/**
 * 카드팩
 *
 * @author 박재익
 *
 */
@Entity
@Table(name = "CARDPACKS")
public class Cardpack {

	private static final String BASE_PATH = "com.genius.flashcard.hibernate.type.";

	/**
	 * 카드팩ID
	 */
	@Id
	@GeneratedValue
	@Column(name = "CARDPACK_ID")
	long cardpackId;

	/**
	 * 문서버전
	 */
	@Column(name = "DOC_VER")
	String docVer;

	/**
	 * 카드팩명
	 */
	@Column(name = "CARDPACK_NAME", nullable = false)
	String cardpackName;

	/**
	 * 소유유저ID
	 */
	@Column(name = "OWNER_USER_ID", nullable = false)
	String ownerUserId;

	/**
	 * 다운로드수
	 */
	@Column(name = "DOWNLOAD_CNT")
	long downloadCnt = 0;

	/**
	 * 상태코드
	 */
	@Type(type = BASE_PATH + "CardpackStatusCdEnumType")
	@Column(name = "STATUS_CD")
	CardpackStatusCdEnum statusCd;

	/**
	 * 접근권한코드
	 */
	@Column(name = "ACCESS_CD")
	@Type(type = BASE_PATH + "CardpackAccessCdEnumType")
	CardpackAccessCdEnum accessCd;

	/**
	 * 카드팩 카테고리 ID
	 */
	@Column(name = "CARDPACK_CATEGORY_ID")
	String cardpackCategoryId;

	/**
	 * 최근액세스일시
	 */
	@Column(name = "LAST_ACCESS_DATE")
	Date lastAccessDate;

	/**
	 * 생성일시
	 */
	@Column(name = "CREATED_DATE", nullable = false)
	Date createdDate;

	/**
	 * S3 키
	 */
	@Column(name = "S3_KEY")
	String s3Key;

	/**
	 * 카드개수
	 */
	@Column(name = "CARD_CNT")
	int cardCnt = 0;

	/**
	 * 삭제여부
	 */
	@Column(name = "IS_DELETE")
	boolean isDelete;

	public long getCardpackId() {
		return cardpackId;
	}

	public void setCardpackId(long cardpackId) {
		this.cardpackId = cardpackId;
	}

	public String getDocVer() {
		return docVer;
	}

	public void setDocVer(String docVer) {
		this.docVer = docVer;
	}

	public String getCardpackName() {
		return cardpackName;
	}

	public void setCardpackName(String cardpackName) {
		this.cardpackName = cardpackName;
	}

	public String getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(String ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public long getDownloadCnt() {
		return downloadCnt;
	}

	public void setDownloadCnt(long downloadCnt) {
		this.downloadCnt = downloadCnt;
	}

	public CardpackStatusCdEnum getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(CardpackStatusCdEnum statusCd) {
		this.statusCd = statusCd;
	}

	public CardpackAccessCdEnum getAccessCd() {
		return accessCd;
	}

	public void setAccessCd(CardpackAccessCdEnum accessCd) {
		this.accessCd = accessCd;
	}

	public String getCardpackCategoryId() {
		return cardpackCategoryId;
	}

	public void setCardpackCategoryId(String cardpackCategoryId) {
		this.cardpackCategoryId = cardpackCategoryId;
	}

	public Date getLastAccessDate() {
		return lastAccessDate;
	}

	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getS3Key() {
		return s3Key;
	}

	public void setS3Key(String s3Key) {
		this.s3Key = s3Key;
	}

	public int getCardCnt() {
		return cardCnt;
	}

	public void setCardCnt(int cardCnt) {
		this.cardCnt = cardCnt;
	}

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
}
