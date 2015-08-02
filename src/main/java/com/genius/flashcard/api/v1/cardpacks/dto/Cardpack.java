package com.genius.flashcard.api.v1.cardpacks.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
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
	@Column(name = "CARDPACK_ID", length = 20, unique = true, nullable = false)
	@GenericGenerator(name = "seq_id", strategy = "com.genius.flashcard.api.v1.cardpacks.dao.StringKeyGenerator")
	@GeneratedValue(generator = "seq_id")
	String cardpackId;

	/**
	 * 문서버전
	 */
	@Column(name = "DOC_VER", length = 10)
	String docVer;

	/**
	 * 카드팩명
	 */
	@Column(name = "CARDPACK_NAME", length = 4000, nullable = false)
	String cardpackName;

	/**
	 * 소유유저ID
	 */
	@Column(name = "OWNER_USER_ID", length = 50, nullable = false)
	String ownerUserId;

	/**
	 * 스토어노출여부
	 */
	@Column(name = "IS_EXPOSURE_STORE")
	boolean isExposureStore = false;

	/**
	 * 사본허용여부
	 */
	@Column(name = "IS_ALLOW_COPY")
	boolean isAllowCopy = false;

	/**
	 * 다운로드수
	 */
	@Column(name = "DOWNLOAD_CNT", length = 10)
	long downloadCnt = 0;

	/**
	 * 상태코드
	 */
	@Type(type = BASE_PATH + "CardpackStatusCdEnumType")
	@Column(name = "CARDPACK_STATUS_CD", length = 20)
	CardpackStatusCdEnum cardpackStatusCd;

	/**
	 * 접근권한코드
	 */
	@Column(name = "CARDPACK_ACCESS_CD", length = 20)
	@Type(type = BASE_PATH + "CardpackAccessCdEnumType")
	CardpackAccessCdEnum cardpackAccessCd;

	/**
	 * 카드팩 카테고리 ID
	 */
	@Column(name = "CARDPACK_CATEGORY_ID", length = 20)
	String cardpackCategoryId;

	/**
	 * 최근액세스일시
	 */
	@Column(name = "LAST_ACCESS_DATE")
	Date lastAccessDate;

	/**
	 * S3 키
	 */
	@Column(name = "S3_KEY", length = 500)
	String s3Key;

	/**
	 * 카드개수
	 */
	@Column(name = "CARD_CNT", length = 5)
	int cardCnt = 0;

	/**
	 * 삭제여부
	 */
	@Column(name = "IS_DELETE")
	boolean isDelete;

	/**
	 * 수정 유저ID
	 */
	@Column(name = "MODIFIER_USER_ID", length = 50)
	String modifierUserId;

	/**
	 * 수정일시
	 */
	@Column(name = "MODIFIED_DATE")
	Date modifiedDate;

	/**
	 * 생성일시
	 */
	@Column(name = "CREATED_DATE", nullable = false)
	Date createdDate;

	public String getModifierUserId() {
		return modifierUserId;
	}

	public void setModifierUserId(String modifierUserId) {
		this.modifierUserId = modifierUserId;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCardpackId() {
		return cardpackId;
	}

	public void setCardpackId(String cardpackId) {
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

	public CardpackStatusCdEnum getCardpackStatusCd() {
		return cardpackStatusCd;
	}

	public void setCardpackStatusCd(CardpackStatusCdEnum cardpackStatusCd) {
		this.cardpackStatusCd = cardpackStatusCd;
	}

	public CardpackAccessCdEnum getCardpackAccessCd() {
		return cardpackAccessCd;
	}

	public void setCardpackAccessCd(CardpackAccessCdEnum cardpackAccessCd) {
		this.cardpackAccessCd = cardpackAccessCd;
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

	public boolean isExposureStore() {
		return isExposureStore;
	}

	public void setExposureStore(boolean isExposureStore) {
		this.isExposureStore = isExposureStore;
	}

	public boolean isAllowCopy() {
		return isAllowCopy;
	}

	public void setAllowCopy(boolean isAllowCopy) {
		this.isAllowCopy = isAllowCopy;
	}
}
