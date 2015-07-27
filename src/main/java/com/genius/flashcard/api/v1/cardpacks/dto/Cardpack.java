package com.genius.flashcard.api.v1.cardpacks.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
	long downloadCnt;

	/**
	 * 상태코드 - 작성중, 서비스중, 정지, 만료(일정기간 엑세스 없는 경우)
	 */
	@Column(name = "STATUS_CD")
	String statusCd;

	/**
	 * 접근권한코드 - 모두, 특정인, 본인
	 */
	@Column(name = "ACCESS_CD")
	String accessCd;

	/**
	 * 카드팩 카테고리 ID
	 */
	@Column(name = "CARDPACK_CATEGORY_ID")
	String cardpackCategoryId;

	/**
	 * 좋아요 수
	 */
	@Column(name = "LIKE_CNT")
	long likeCnt;

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

	public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public String getAccessCd() {
		return accessCd;
	}

	public void setAccessCd(String accessCd) {
		this.accessCd = accessCd;
	}

	public String getCardpackCategoryId() {
		return cardpackCategoryId;
	}

	public void setCardpackCategoryId(String cardpackCategoryId) {
		this.cardpackCategoryId = cardpackCategoryId;
	}

	public long getLikeCnt() {
		return likeCnt;
	}

	public void setLikeCnt(long likeCnt) {
		this.likeCnt = likeCnt;
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
}
