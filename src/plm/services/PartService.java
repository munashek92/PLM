package plm.services;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plm.dao.DocumentDao;
import plm.dao.PartDao;
import plm.model.Document;
import plm.model.Part;
import plm.util.Util;

@Service
public class PartService {
	
	@Autowired
	private PartDao partDao;

	@Autowired
	private DocumentDao documentDao;
	
	private static Logger LOGGER = LoggerFactory.getLogger(PartService.class);
	
   public void reserve(String userId, String reference, String version, int iteration) {
		
    	Part part = partDao.get(reference, version, iteration);
    	
    	if (!part.isReserved() && !part.getLifeCycleTemplate().isFinal(part.getLifeCycleState())) {

    		Part nextPartIteration = new Part(part.getReference(), part.getVersion(), iteration + 1);
    		
    		nextPartIteration.setReserved(true);
    		nextPartIteration.setReservedBy(userId);
    		
    		nextPartIteration.setLifeCycleTemplate(part.getLifeCycleTemplate());
    		nextPartIteration.setLifeCycleState(part.getLifeCycleState());

    		nextPartIteration.setVersionSchema(part.getVersionSchema());
    		
    		nextPartIteration.setPartAttribute1(part.getPartAttribute1());
    		nextPartIteration.setPartAttribute2(part.getPartAttribute2());
    		
    		partDao.create(nextPartIteration);
    		
    		for (Document document : getLinkedDocuments(part)) {
    			Document nextIteration = new Document(document.getReference(), document.getVersion(), iteration + 1);
        		
        		nextIteration.setReserved(true);
        		nextIteration.setReservedBy(userId);
        		
        		nextIteration.setLifeCycleTemplate(document.getLifeCycleTemplate());
        		nextIteration.setLifeCycleState(document.getLifeCycleState());

        		nextIteration.setVersionSchema(document.getVersionSchema());
        		
        		nextIteration.setDocumentAttribute1(document.getDocumentAttribute1());
        		nextIteration.setDocumentAttribute2(document.getDocumentAttribute2());
        		
        		documentDao.create(nextIteration);
			}
    	}
	}

	public void update(String userId, String reference, String version, int iteration, String partAttribute1, String partAttribute2) {

    	Part part = partDao.get(reference, version, iteration);
    	
    	if (part.isReserved() && part.getReservedBy().equals(userId)) {
    		
    		part.setPartAttribute1(partAttribute1);
    		part.setPartAttribute2(partAttribute2);
    		
    		partDao.update(part);
    	}
	}

	public void free(String userId, String reference, String version, int iteration) {
		
    	Part part = partDao.get(reference, version, iteration);
    	
    	if (part.isReserved() && part.getReservedBy().equals(userId)) {

    		part.setReserved(false);
    		part.setReservedBy(null);

    		partDao.update(part);
    		
    		for (Document document : getLinkedDocuments(part)) {
        		
    			document.setReserved(false);
    			document.setReservedBy(null);
        		
        		documentDao.update(document);
			}
    	}
	}

	public void setState(String userId, String reference, String version, int iteration, String state) {
		
    	Part part = partDao.get(reference, version, iteration);
    	
    	if (!part.isReserved() && part.getLifeCycleTemplate().isKnown(state)) {
    		
    		part.setLifeCycleState(state);

    		partDao.update(part);
    		
    		for (Document document : getLinkedDocuments(part)) {
        		
    			document.setLifeCycleState(state);
        		
        		documentDao.update(document);
			}
    	}
	}

	public void revise(String userId, String reference, String version, int iteration) {
		
    	Part part = partDao.get(reference, version, iteration);
    	
    	if (!part.isReserved() && part.getLifeCycleTemplate().isFinal(part.getLifeCycleState())) {

    		Part nextPartVersion = new Part(part.getReference(), part.getVersionSchema().getNextVersionLabel(version), 1);
    		
    		nextPartVersion.setReserved(false);
    		nextPartVersion.setReservedBy(null);
    		
    		nextPartVersion.setLifeCycleTemplate(part.getLifeCycleTemplate());
    		nextPartVersion.setLifeCycleState(part.getLifeCycleTemplate().getInitialState());

    		nextPartVersion.setVersionSchema(part.getVersionSchema());
    		
    		nextPartVersion.setPartAttribute1(part.getPartAttribute1());
    		nextPartVersion.setPartAttribute2(part.getPartAttribute2());
    		
    		partDao.create(nextPartVersion);

    		for (Document document : getLinkedDocuments(part)) {
        		
    			Document nextDocumentVersion = new Document(document.getReference(), document.getVersionSchema().getNextVersionLabel(version), 1);
        		
        		nextDocumentVersion.setReserved(false);
        		nextDocumentVersion.setReservedBy(null);
        		
        		nextDocumentVersion.setLifeCycleTemplate(document.getLifeCycleTemplate());
        		nextDocumentVersion.setLifeCycleState(document.getLifeCycleTemplate().getInitialState());

        		nextDocumentVersion.setVersionSchema(document.getVersionSchema());
        		
        		nextDocumentVersion.setDocumentAttribute1(document.getDocumentAttribute1());
        		nextDocumentVersion.setDocumentAttribute2(document.getDocumentAttribute2());
        		
        		documentDao.create(nextDocumentVersion);
			}
    	}
	}
	
	
	private Set<Document> getLinkedDocuments(Part part) {
    	//
		// Implementation and returned value are not relevant for this exercise
    	//
		return null;
	}
}
