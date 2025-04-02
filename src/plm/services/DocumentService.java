package plm.services;

import org.springframework.stereotype.Service;

import plm.dao.DocumentDao;
import plm.model.Document;

@Service
public class DocumentService {

    private final DocumentDao documentDao;

    public DocumentService(DocumentDao documentDao) {
       this.documentDao = documentDao;
    }


    public void reserve(String userId, String reference, String version, int iteration)  {

        Document document = documentDao.get(reference, version, iteration);

        if(document == null) {
            throw new NullPointerException("document cannot be null");
        }

        if(document.isReserved()) {
            throw new IllegalArgumentException("document is already reserved");
        }

        if(document.getLifeCycleTemplate().isFinal(document.getLifeCycleState())) {
            throw new IllegalArgumentException("Cannot reserve a document in its final life cycle state");
        }

        Document nextIteration = new Document(document.getReference(), document.getVersion(), iteration + 1);

        nextIteration.setReserved(true);
        nextIteration.setReservedBy(userId);
        //
        nextIteration.setLifeCycleTemplate(document.getLifeCycleTemplate());
        nextIteration.setLifeCycleState(document.getLifeCycleState());
        //
        nextIteration.setVersionSchema(document.getVersionSchema());
        //
        nextIteration.setDocumentAttribute1(document.getDocumentAttribute1());
        nextIteration.setDocumentAttribute2(document.getDocumentAttribute2());
        //
        documentDao.create(nextIteration);

    }

    public void update(String userId, String reference, String version, int iteration, String documentAttribute1, String documentAttribute2) {
        //
        Document document = documentDao.get(reference, version, iteration);

        if (document == null) {
            throw new IllegalArgumentException("Document not found");
        }
        if (!document.isReserved() || !document.getReservedBy().equals(userId)) {
            throw new IllegalStateException("Document is not reserved by the user");
        }

        document.setDocumentAttribute1(documentAttribute1);
        document.setDocumentAttribute2(documentAttribute2);
        //
        documentDao.update(document);
    }

    public void free(String userId, String reference, String version, int iteration) {

        Document document = documentDao.get(reference, version, iteration);

        if (document == null) {
            throw new IllegalArgumentException("Document not found");
        }
        if (!document.isReserved() || !document.getReservedBy().equals(userId)) {
            throw new IllegalStateException("Document is not reserved by the user");
        }

        if (isNotLinkedToPart(document)) {
            document.setReserved(false);
            document.setReservedBy(null);
            documentDao.update(document);
        }
    }

    public void setState(String userId, String reference, String version, int iteration, String state) throws RuntimeException {

        Document document = documentDao.get(reference, version, iteration);

        if(document == null) {
            throw new NullPointerException("document cannot be null");
        }

        if(document.isReserved()) {
            throw new IllegalArgumentException("document is already reserved");
        }

        if(document.getLifeCycleTemplate().isFinal(document.getLifeCycleState())) {
            throw new IllegalArgumentException("Cannot reserve a document in its final life cycle state");
        }

        document.setLifeCycleState(state);
        documentDao.update(document);
    }

    public void revise(String userId, String reference, String version, int iteration) throws RuntimeException{

        Document document = documentDao.get(reference, version, iteration);
        if(document == null) {
            throw new NullPointerException("document cannot be null");
        }

        if(document.getLifeCycleTemplate().isFinal(document.getLifeCycleState())) {
            throw new IllegalArgumentException("Cannot reserve a document in its final life cycle state");
        }

        Document nextVersion = new Document(document.getReference(), document.getVersionSchema().getNextVersionLabel(version), 1);
        //
        nextVersion.setReserved(false);
        nextVersion.setReservedBy(null);
        //
        nextVersion.setLifeCycleTemplate(document.getLifeCycleTemplate());
        nextVersion.setLifeCycleState(document.getLifeCycleTemplate().getInitialState());
        //
        nextVersion.setVersionSchema(document.getVersionSchema());
        //
        nextVersion.setDocumentAttribute1(document.getDocumentAttribute1());
        nextVersion.setDocumentAttribute2(document.getDocumentAttribute2());
        //
        documentDao.create(nextVersion);
    }

    private boolean isNotLinkedToPart(Document document) {
        //
        // Implementation and returned value are not relevant for this exercise
        //
        return false;
    }
}




