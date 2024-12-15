package modele;

import java.util.Objects;


public class Document{
	
	private String idBail;
	private String dateDocument;
	private String typeDocument;
	private String urlDocument;
	
	public Document(String idBail, String dateDocument, String typeDocument, String urlDocument) {
		this.idBail = idBail;
		this.dateDocument = dateDocument;
		this.typeDocument = typeDocument;
		this.urlDocument = urlDocument;
	}

	/**
	 * @return le dateDocument
	 */
	public String getDateDocument() {
		return dateDocument;
	}


	/**
	 * @return le typeDocument
	 */
	public String getTypeDocument() {
		return typeDocument;
	}

	/**
	 * @param typeDocument le typeDocument du document
	 */
	public void setTypeDocument(String typeDocument) {
		this.typeDocument = typeDocument;
	}

	/**
	 * @return le urlDocument
	 */
	public String getUrlDocument() {
		return urlDocument;
	}

	/**
	 * @param urlDocument le urlDocument du document
	 */
	public void setUrlDocument(String urlDocument) {
		this.urlDocument = urlDocument;
	}

	/**
	 * @return the idBail
	 */
	public String getIdBail() {
		return idBail;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dateDocument, idBail);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Document)) {
			return false;
		}
		Document other = (Document) obj;
		return Objects.equals(dateDocument, other.dateDocument) && Objects.equals(idBail, other.idBail);
	}

	@Override
	public String toString() {
		return "Document [idBail=" + idBail + ", dateDocument=" + dateDocument + ", typeDocument=" + typeDocument
				+ ", urlDocument=" + urlDocument + "]";
	}

	
	
	
}