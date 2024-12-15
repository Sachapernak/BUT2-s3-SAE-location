package modele.dao;

import java.io.IOException;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import modele.Document;
import modele.dao.requetes.RequeteCreateDocument;
import modele.dao.requetes.RequeteDeleteDocument;
import modele.dao.requetes.RequeteSelectDocumentByIdBail;
import modele.dao.requetes.RequeteSelectDocument;
import modele.dao.requetes.RequeteSelectDocumentById;
import modele.dao.requetes.RequeteUpdateDocument;


public class DaoDocument extends DaoModele<Document> implements Dao<Document> {
    
	@Override
    public void create(Document donnees) throws SQLException, IOException{
		miseAJour(new RequeteCreateDocument(), donnees);
    }
    
    @Override
    public void update(Document donnees) throws SQLException, IOException {
    	miseAJour(new RequeteUpdateDocument(), donnees);
    }
    
    @Override
    public void delete(Document donnees)throws SQLException, IOException {
    	miseAJour(new RequeteDeleteDocument(), donnees);
    }
   
    @Override
    public Document findById(String... id) throws SQLException, IOException {
        return findById(new RequeteSelectDocumentById(), id);
    }
    
    public List<Document> findByIdBail(String... id) throws SQLException, IOException {
        return find(new RequeteSelectDocumentByIdBail(), id);
    }
    
    @Override
    public List<Document> findAll() throws SQLException, IOException {
        return find(new RequeteSelectDocument());
    }
    
    @Override
    protected Document createInstance(ResultSet curseur) throws SQLException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Convertir la chaîne en LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.parse(curseur.getString("DATE_DOCUMENT"), formatter);

        // Extraire uniquement la date si nécessaire
        LocalDate localDate = localDateTime.toLocalDate();
        
        String idBail = curseur.getString("ID_BAIL");
        
        // Formatter la date de début
        String dateDocument = localDate.format(DateTimeFormatter.ofPattern("yyyy-dd-MM"));
        
        String typeDeDocument = curseur.getString("TYPE_DE_DOCUMENT");
        
        String urlDocument = curseur.getString("URL_DOCUMENT");
        
        Document document = new Document(idBail, dateDocument, typeDeDocument, urlDocument);

        return document;
    }
}
