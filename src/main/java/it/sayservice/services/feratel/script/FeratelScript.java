package it.sayservice.services.feratel.script;

import it.sayservice.services.feratel.data.message.Data.Contatto;
import it.sayservice.services.feratel.data.message.Data.Foto;
import it.sayservice.services.feratel.data.message.Data.Posizione;
import it.sayservice.services.feratel.data.message.Data.Servizio;
import it.sayservice.services.feratel.data.message.Data.StrutturaCommerciale;
import it.sayservice.services.feratel.data.message.Data.StrutturaPubblica;
import it.sayservice.services.feratel.data.message.Data.StrutturaRicettiva;
import it.sayservice.services.feratel.data.message.Data.StrutturaRistoro;
import it.sayservice.services.feratel.data.message.Data.StrutturaSportiva;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.protobuf.Message;

public class FeratelScript {
	protected final Log logger = LogFactory.getLog(getClass());
	
	public static final int NOME = 0;
	public static final int INDIRIZZO = 1;
	public static final int FERATEL_ID = 2;
	public static final int FS_ID = 3;
	
	private XPathFactory factory = null;
	private XPath xpath = null;
	private Map<String, KeyValueItem> facilities = null;
	private Map<String, KeyValueItem> categories = null;
	private Map<String, KeyValueItem> stars = null;
	private Map<String, KeyValueItem> classifications = null;
	private List<String> tipiCucina = null;
	private List<String> struttureRistoroCode = null;
	private List<String> struttureCommercialiCode = null;
	private List<String> struttureSportCode = null;
	private List<String> strutturePubblicheCode = null;
	private List<String> lingueFacilities = null;
	XPathExpression expr;
	XPathExpression idExpr;
	XPathExpression valueExpr;
	XPathExpression translateItExpr;
	XPathExpression translateEnExpr;
	XPathExpression nameExpr;
	XPathExpression latitudeExpr;
	XPathExpression longitudeExpr;
	XPathExpression addressExpr;
	XPathExpression zipExpr;
	XPathExpression townExpr;
	XPathExpression fotoExpr;
	XPathExpression fotoUrlExpr;
	XPathExpression fotoDescItExpr;
	XPathExpression fotoDescEnExpr;
	XPathExpression firstNameExpr;
	XPathExpression lastNameExpr;
	XPathExpression phoneExpr;
	XPathExpression faxExpr;
	XPathExpression mobileExpr;
	XPathExpression emailExpr;
	XPathExpression urlExpr;
	XPathExpression codeExpr;
	XPathExpression facilityExpr;
	XPathExpression descItExpr;
	XPathExpression descEnExpr;
	
	public FeratelScript() {
		this.factory = XPathFactory.newInstance();
		this.xpath = factory.newXPath();
		
		this.tipiCucina = new ArrayList<String>();
		this.tipiCucina.add("3c096111-a263-4289-94e9-07092817ddb8");
		this.tipiCucina.add("1973045a-0fb3-4f8b-aa2e-59344ed89655");
		this.tipiCucina.add("0826c6d5-2c72-4a5d-bc30-8c8e7f004685");
		this.tipiCucina.add("0f1d4c4c-b343-4f55-a988-95d4b4d153b7");
		this.tipiCucina.add("d0203d8a-254d-47bf-b248-ce824b39e67c");
		this.tipiCucina.add("74104683-6a39-4e39-8157-cc337f4cdf83");
		this.tipiCucina.add("c53d2a27-98ff-482c-a012-ddc9455a0839");
		this.tipiCucina.add("b8b41bc8-0b12-4bd3-9321-ff1a38eeac5a");
		
		this.lingueFacilities = new ArrayList<String>();
		this.lingueFacilities.add("84b4cd9d-9dc6-4865-92ac-71dad05fdb3f");
		this.lingueFacilities.add("b5c167a8-a2a4-4872-bb5f-af8faaac4918");
		
		this.struttureRistoroCode = new ArrayList<String>();
		this.struttureRistoroCode.add("SRIST");
		
		this.struttureCommercialiCode = new ArrayList<String>();
		this.struttureCommercialiCode.add("SCOMM");
		
		this.struttureSportCode = new ArrayList<String>();
		this.struttureSportCode.add("SSTRU");
		
		this.strutturePubblicheCode = new ArrayList<String>();
		this.strutturePubblicheCode.add("SUTIL");
		
	}
	
	public void setCommonExpr() throws Exception {
		expr = xpath.compile("/FeratelDsiRS/Result/ServiceProviders/ServiceProvider");
		idExpr = xpath.compile("./@Id");
		valueExpr = xpath.compile("./@Value");
		translateItExpr = xpath.compile("./Name/Translation[@Language='it']");
		translateEnExpr = xpath.compile("./Name/Translation[@Language='en']");
		nameExpr = xpath.compile("./Details/Name/text()");
		latitudeExpr = xpath.compile("./Details/Position/@Latitude");
		longitudeExpr = xpath.compile("./Details/Position/@Longitude");
		codeExpr = xpath.compile("./Details/Code/text()");
		facilityExpr = xpath.compile("./Facilities/Facility");
		descItExpr = xpath.compile("./Descriptions/Description[@Language='it']/text()");
		descEnExpr = xpath.compile("./Descriptions/Description[@Language='en']/text()");
		//address
		addressExpr = xpath.compile("./Addresses/Address[@Type='Object']/AddressLine1/text()");
		zipExpr = xpath.compile("./Addresses/Address[@Type='Object']/ZipCode/text()");
		townExpr = xpath.compile("./Addresses/Address[@Type='Object']/Town/text()");
		//foto
		fotoExpr = xpath.compile("./Documents/Document[@Class='Image']");
		fotoUrlExpr = xpath.compile("./URL/text()");
		fotoDescItExpr = xpath.compile("./Names/Translation[@Language='it']");
		fotoDescEnExpr = xpath.compile("./Names/Translation[@Language='en']");
		//contatto
		firstNameExpr = xpath.compile("./Addresses/Address[@Type='Object']/FirstName/text()");
		lastNameExpr = xpath.compile("./Addresses/Address[@Type='Object']/LastName/text()");
		phoneExpr = xpath.compile("./Addresses/Address[@Type='Object']/Phone/text()");
		faxExpr = xpath.compile("./Addresses/Address[@Type='Object']/Fax/text()");
		mobileExpr = xpath.compile("./Addresses/Address[@Type='Object']/Mobile/text()");
		emailExpr = xpath.compile("./Addresses/Address[@Type='Object']/Email/text()");
		urlExpr = xpath.compile("./Addresses/Address[@Type='Object']/URL/text()");
	}
	
	public boolean isEmpty(String string) {
		if((string != null) && !string.isEmpty()) {
			return false;
		}
		return true;
	}
  
  public List<Message> getStrutturePubbliche(Document xmlStrutture, Document keyvaluesDoc) throws Exception {
  	List<Message> result = new ArrayList<Message>();
  	setCommonExpr();
  	setFacilities(keyvaluesDoc, idExpr, translateItExpr, translateEnExpr);
		
  	NodeList nodes = (NodeList) expr.evaluate(xmlStrutture, XPathConstants.NODESET);
		for (int i = 0; i < nodes.getLength(); i++) {
			Node singleNode = nodes.item(i);
			singleNode.getParentNode().removeChild(singleNode);
			Element service = (Element) singleNode;
			
			String code = (String) codeExpr.evaluate(service, XPathConstants.STRING);
			try {
				code = code.substring(0, 5);
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println(i + " wrong code:" +code);
				continue;
			}
			if(strutturePubblicheCode.contains(code)) {
				String id = (String) idExpr.evaluate(service, XPathConstants.STRING);
				String nome = (String) nameExpr.evaluate(service, XPathConstants.STRING);
				String indirizzo = getIndirizzo(service, addressExpr, zipExpr, townExpr);
				String tipo = getTipoByFacilities(service, lingueFacilities, facilityExpr, idExpr);
				String descIt = (String) descItExpr.evaluate(service, XPathConstants.STRING);
				String descEn = (String) descEnExpr.evaluate(service, XPathConstants.STRING);
				
				Posizione posizione = getPosizione(service, latitudeExpr, longitudeExpr);
				List<Foto> foto = getFoto(service, fotoExpr, fotoUrlExpr, fotoDescItExpr, fotoDescEnExpr);
				Contatto contatto = getContatto(service, firstNameExpr, lastNameExpr, phoneExpr, faxExpr, mobileExpr, emailExpr, urlExpr);
				
				StrutturaPubblica.Builder struttura = StrutturaPubblica.newBuilder();
				struttura.setId(id);
				if(!isEmpty(tipo)) {
					struttura.setTipo(tipo);
				} else {
					System.out.println(i + " empty type:" + id + " - " + nome);
					continue;
				}
				struttura.setNome(nome);
				struttura.setIndirizzo(indirizzo);
				if(posizione != null) {
					System.out.println(i + " :" + id);
					struttura.setPosizione(posizione);
				} else {
					System.out.println(i + " empty position:" + id + " - " + nome);
					continue;
				}
				if(!isEmpty(descIt)) 
					struttura.setDescrizioneIt(descIt);
				if(!isEmpty(descEn))
					struttura.setDescrizioneEn(descEn);
				struttura.addAllFoto(foto);
				struttura.setContatto(contatto);
				
				result.add(struttura.build());
			}
		}
  	return result;
  }
  
  public List<Message> getStruttureSportive(Document xmlStrutture, Document keyvaluesDoc) throws Exception {
  	List<Message> result = new ArrayList<Message>();
  	setCommonExpr();
  	setFacilities(keyvaluesDoc, idExpr, translateItExpr, translateEnExpr);
		
  	NodeList nodes = (NodeList) expr.evaluate(xmlStrutture, XPathConstants.NODESET);
		for (int i = 0; i < nodes.getLength(); i++) {
			try {
				Node singleNode = nodes.item(i);
				singleNode.getParentNode().removeChild(singleNode);
				Element service = (Element) singleNode;

				String code = (String) codeExpr.evaluate(service, XPathConstants.STRING);
				try {
					code = code.substring(0, 5);
				} catch (StringIndexOutOfBoundsException e) {
					if(logger.isWarnEnabled())
						logger.warn(i + " wrong code:" +code);
					continue;
				}
				if(struttureSportCode.contains(code)) {
					String id = (String) idExpr.evaluate(service, XPathConstants.STRING);
					String nome = (String) nameExpr.evaluate(service, XPathConstants.STRING);
					String indirizzo = getIndirizzo(service, addressExpr, zipExpr, townExpr);
					String tipo = getTipoByFacilities(service, lingueFacilities, facilityExpr, idExpr);
					String descIt = (String) descItExpr.evaluate(service, XPathConstants.STRING);
					String descEn = (String) descEnExpr.evaluate(service, XPathConstants.STRING);
					
					Posizione posizione = getPosizione(service, latitudeExpr, longitudeExpr);
					List<Foto> foto = getFoto(service, fotoExpr, fotoUrlExpr, fotoDescItExpr, fotoDescEnExpr);
					Contatto contatto = getContatto(service, firstNameExpr, lastNameExpr, phoneExpr, faxExpr, mobileExpr, emailExpr, urlExpr);
					
					StrutturaSportiva.Builder struttura = StrutturaSportiva.newBuilder();
					struttura.setId(id);
					if(!isEmpty(tipo)) {
						struttura.setTipo(tipo);
					} else {
						if(logger.isWarnEnabled())
							logger.warn(i + " empty type:" + id + " - " + nome);
						continue;
					}
					struttura.setNome(nome);
					struttura.setIndirizzo(indirizzo);
					if(posizione != null) {
						if(logger.isInfoEnabled())
							logger.info(i + " :" + id);
						struttura.setPosizione(posizione);
					} else {
						if(logger.isWarnEnabled())
							logger.warn(i + " empty position:" + id + " - " + nome);
						continue;
					}
					if(!isEmpty(descIt)) 
						struttura.setDescrizioneIt(descIt);
					if(!isEmpty(descEn))
						struttura.setDescrizioneEn(descEn);
					struttura.addAllFoto(foto);
					struttura.setContatto(contatto);

					result.add(struttura.build());
				}
			} catch (Exception e) {
				logger.error("error", e);
			}
		}
  	return result;
  }
  
  public List<Message> getStruttureCommerciali(Document xmlStrutture, Document keyvaluesDoc) throws Exception {
  	List<Message> result = new ArrayList<Message>();
  	setCommonExpr();
  	setFacilities(keyvaluesDoc, idExpr, translateItExpr, translateEnExpr);
  	
		NodeList nodes = (NodeList) expr.evaluate(xmlStrutture, XPathConstants.NODESET);
		for (int i = 0; i < nodes.getLength(); i++) {
			try {
				Node singleNode = nodes.item(i);
				singleNode.getParentNode().removeChild(singleNode);
				Element service = (Element) singleNode;

				String code = (String) codeExpr.evaluate(service, XPathConstants.STRING);
				try {
					code = code.substring(0, 5);
				} catch (StringIndexOutOfBoundsException e) {
					if(logger.isWarnEnabled())
						logger.warn(i + " wrong code:" +code);
					continue;
				}
				if(struttureCommercialiCode.contains(code)) {
					String id = (String) idExpr.evaluate(service, XPathConstants.STRING);
					String nome = (String) nameExpr.evaluate(service, XPathConstants.STRING);
					String indirizzo = getIndirizzo(service, addressExpr, zipExpr, townExpr);
					String tipo = getTipoByFacilities(service, lingueFacilities, facilityExpr, idExpr);
					String descIt = (String) descItExpr.evaluate(service, XPathConstants.STRING);
					String descEn = (String) descEnExpr.evaluate(service, XPathConstants.STRING);
					
					Posizione posizione = getPosizione(service, latitudeExpr, longitudeExpr);
					List<Foto> foto = getFoto(service, fotoExpr, fotoUrlExpr, fotoDescItExpr, fotoDescEnExpr);
					Contatto contatto = getContatto(service, firstNameExpr, lastNameExpr, phoneExpr, faxExpr, mobileExpr, emailExpr, urlExpr);
					
					StrutturaCommerciale.Builder struttura = StrutturaCommerciale.newBuilder();
					struttura.setId(id);
					if(!isEmpty(tipo)) {
						struttura.setTipo(tipo);
					} else {
						if(logger.isWarnEnabled())
							logger.warn(i + " empty type:" + id + " - " + nome);
						continue;
					}
					struttura.setNome(nome);
					struttura.setIndirizzo(indirizzo);
					if(posizione != null) {
						if(logger.isInfoEnabled())
							logger.info(i + " :" + id);
						struttura.setPosizione(posizione);
					} else {
						if(logger.isWarnEnabled())
							logger.warn(i + " empty position:" + id + " - " + nome);
						continue;
					}
					if(!isEmpty(descIt)) 
						struttura.setDescrizioneIt(descIt);
					if(!isEmpty(descEn))
						struttura.setDescrizioneEn(descEn);
					struttura.addAllFoto(foto);
					struttura.setContatto(contatto);
					
					result.add(struttura.build());
				}
			} catch (Exception e) {
				logger.error("error", e);
			}
		}
  	return result;
  }
  
  public List<Message> getStruttureRistoro(Document xmlStrutture, Document keyvaluesDoc) throws Exception {
  	List<Message> result = new ArrayList<Message>();
  	setCommonExpr();
  	setFacilities(keyvaluesDoc, idExpr, translateItExpr, translateEnExpr);
  	
		NodeList nodes = (NodeList) expr.evaluate(xmlStrutture, XPathConstants.NODESET);
		for (int i = 0; i < nodes.getLength(); i++) {
			try {
				Node singleNode = nodes.item(i);
				singleNode.getParentNode().removeChild(singleNode);
				Element service = (Element) singleNode;

				String code = (String) codeExpr.evaluate(service, XPathConstants.STRING);
				try {
					code = code.substring(0, 5);
				} catch (StringIndexOutOfBoundsException e) {
					if(logger.isWarnEnabled())
						logger.warn(i + " wrong code:" +code);
					continue;
				}
				if(struttureRistoroCode.contains(code)) {
					String id = (String) idExpr.evaluate(service, XPathConstants.STRING);
					String nome = (String) nameExpr.evaluate(service, XPathConstants.STRING);
					String indirizzo = getIndirizzo(service, addressExpr, zipExpr, townExpr);
					String tipo = getTipoByFacilities(service, lingueFacilities, facilityExpr, idExpr);
					String descIt = (String) descItExpr.evaluate(service, XPathConstants.STRING);
					String descEn = (String) descEnExpr.evaluate(service, XPathConstants.STRING);

					Posizione posizione = getPosizione(service, latitudeExpr, longitudeExpr);
					List<Foto> foto = getFoto(service, fotoExpr, fotoUrlExpr, fotoDescItExpr, fotoDescEnExpr);
					Contatto contatto = getContatto(service, firstNameExpr, lastNameExpr, phoneExpr, faxExpr, mobileExpr, emailExpr, urlExpr);
					
					StrutturaRistoro.Builder struttura = StrutturaRistoro.newBuilder();
					struttura.setId(id);
					if(!isEmpty(tipo)) {
						struttura.setTipo(tipo);
					} else {
						if(logger.isWarnEnabled())
							logger.warn(i + " empty type:" + id + " - " + nome);
						continue;
					}
					struttura.setNome(nome);
					struttura.setIndirizzo(indirizzo);
					if(posizione != null) {
						if(logger.isInfoEnabled())
							logger.info(i + " :" + id);
						struttura.setPosizione(posizione);
					} else {
						if(logger.isWarnEnabled())
							logger.warn(i + " empty position:" + id + " - " + nome);
						continue;
					}
					if(!isEmpty(descIt)) 
						struttura.setDescrizioneIt(descIt);
					if(!isEmpty(descEn))
						struttura.setDescrizioneEn(descEn);
					struttura.addAllFoto(foto);
					struttura.setContatto(contatto);
					
					result.add(struttura.build());
				}
			} catch (Exception e) {
				logger.error("error", e);
			}
		}
  	return result;
  }
  
	private String getTipoByFacilities(Element service, List<String> toExcluded, XPathExpression serviceExpr, 
  		XPathExpression idExpr) throws Exception {
		String tipo = "";
		NodeList nodes = (NodeList) serviceExpr.evaluate(service, XPathConstants.NODESET);
		for (int i = 0; i < nodes.getLength(); i++) {
			Element document = (Element) nodes.item(i);
			String id = (String) idExpr.evaluate(document, XPathConstants.STRING);
			if(!toExcluded.contains(id.toLowerCase())) {
				KeyValueItem item = facilities.get(id.toLowerCase());
				tipo = tipo + item.getDescIt() + " ";
			}
		}
		return tipo.trim();
	}

	public List<Message> getStruttureRicettive(Document xmlStrutture, Document keyvaluesDoc) throws Exception {
  	List<Message> result = new ArrayList<Message>();
  	
  	setCommonExpr();
  	
		//path for accomodations
		XPathExpression categoryIdExpr = xpath.compile("./Details/Categories/Item/@Id");
		XPathExpression roomExpr = xpath.compile("./Details/Rooms/text()");
		XPathExpression bedsExpr = xpath.compile("./Details/Beds/text()");
		//servizi
		
		XPathExpression starIdExpr = xpath.compile("./Details/Stars/@Id");
		XPathExpression classificationIdExpr = xpath.compile("./Details/Classifications/Item/@Id");
		
  	setCategories(keyvaluesDoc, idExpr, translateItExpr, translateEnExpr);
  	setFacilities(keyvaluesDoc, idExpr, translateItExpr, translateEnExpr);
  	setStars(keyvaluesDoc, idExpr, translateItExpr, translateEnExpr);
  	setClassifications(keyvaluesDoc, idExpr, translateItExpr, translateEnExpr);
  	
		NodeList nodes = (NodeList) expr.evaluate(xmlStrutture, XPathConstants.NODESET);
		for (int i = 0; i < nodes.getLength(); i++) {
			try {
				Node singleNode = nodes.item(i);
				singleNode.getParentNode().removeChild(singleNode);
				Element service = (Element) singleNode;
				
				String id = (String) idExpr.evaluate(service, XPathConstants.STRING);
				String nome = (String) nameExpr.evaluate(service, XPathConstants.STRING);
				String indirizzo = getIndirizzo(service, addressExpr, zipExpr, townExpr);
				String categoryId = (String) categoryIdExpr.evaluate(service, XPathConstants.STRING);
				String tipo = getTipo(service, categoryId);
				String categoriaIt = getClassification(service, starIdExpr, classificationIdExpr, "it");
				String categoriaEn = getClassification(service, starIdExpr, classificationIdExpr, "en");
				String descIt = (String) descItExpr.evaluate(service, XPathConstants.STRING);
				String descEn = (String) descEnExpr.evaluate(service, XPathConstants.STRING);
				String room = (String) roomExpr.evaluate(service, XPathConstants.STRING);
				String bads = (String) bedsExpr.evaluate(service, XPathConstants.STRING);
				
				Posizione posizione = getPosizione(service, latitudeExpr, longitudeExpr);
				List<Foto> foto = getFoto(service, fotoExpr, fotoUrlExpr, fotoDescItExpr, fotoDescEnExpr);
				List<Servizio> servizi = getServizi(service, facilityExpr, idExpr, valueExpr);
				Contatto contatto = getContatto(service, firstNameExpr, lastNameExpr, phoneExpr, faxExpr, mobileExpr, emailExpr, urlExpr);
				
				StrutturaRicettiva.Builder struttura = StrutturaRicettiva.newBuilder();
				struttura.setId(id);
				struttura.setTipo(tipo);
				struttura.setNome(nome);
				struttura.setIndirizzo(indirizzo);
				if(posizione != null) {
					if(logger.isInfoEnabled())
						logger.info(i + " :" + id + " - " + nome);
					struttura.setPosizione(posizione);
				} else {
					if(logger.isWarnEnabled())
						logger.warn(i + " empty position:" + id + " - " + nome);
					continue;
				}
				struttura.setCategoriaIt(categoriaIt);
				struttura.setCategoriaEn(categoriaEn);
				struttura.setDescrizioneIt(descIt);
				struttura.setDescrizioneEn(descEn);
				struttura.setCamere(room);
				struttura.setLetti(bads);
				struttura.addAllFoto(foto);
				struttura.addAllServizio(servizi);
				struttura.setContatto(contatto);
				
				result.add(struttura.build());
			} catch (Exception e) {
				logger.error("error", e);
			}
		}
  	return result;
  }

	private String getClassification(Element service, XPathExpression starIdExpr, XPathExpression classificationIdExpr, String language) throws Exception {
		String starId = (String) starIdExpr.evaluate(service, XPathConstants.STRING);
		if((starId != null) && (!starId.isEmpty())) {
			KeyValueItem item = stars.get(starId.toLowerCase());
			if(item != null) {
				return language.equals("it") ? item.getDescIt() : item.getDescEn(); 
			} else {
				return "";
			}
		} else {
			String classificationId = (String) classificationIdExpr.evaluate(service, XPathConstants.STRING);
			if((classificationId != null) && (!classificationId.isEmpty())) {
				KeyValueItem item = classifications.get(classificationId.toLowerCase());
				if(item != null) {
					return language.equals("it") ? item.getDescIt() : item.getDescEn(); 
				} else {
					return "";
				}
			}
		}
		return "";
	}
	
	private Contatto getContatto(Element service, XPathExpression firstNameExpr, XPathExpression lastNameExpr,
		XPathExpression phoneExpr, XPathExpression faxExpr, XPathExpression mobileExpr, 
		XPathExpression emailExpr, XPathExpression urlExpr) throws Exception {
		String firstName = (String) firstNameExpr.evaluate(service, XPathConstants.STRING);
		String lastName = (String) lastNameExpr.evaluate(service, XPathConstants.STRING);
		String phone = (String) phoneExpr.evaluate(service, XPathConstants.STRING);
		String fax = (String) faxExpr.evaluate(service, XPathConstants.STRING);
		String mobile = (String) mobileExpr.evaluate(service, XPathConstants.STRING);
		String email = (String) emailExpr.evaluate(service, XPathConstants.STRING);
		String url = (String) urlExpr.evaluate(service, XPathConstants.STRING);
		
		Contatto.Builder contatto = Contatto.newBuilder();
		contatto.setNome(firstName + " " + lastName);
		contatto.setTelefono(phone);
		contatto.setFax(fax);
		contatto.setCell(mobile);
		contatto.setEmail(email);
		contatto.setUrl(url);
		
		return contatto.build();
	}

	private List<Servizio> getServizi(Element service, XPathExpression serviceExpr, 
		XPathExpression idExpr, XPathExpression valueExpr) throws Exception {
		List<Servizio> result = new ArrayList<Servizio>();
		NodeList nodes = (NodeList) serviceExpr.evaluate(service, XPathConstants.NODESET);
		for (int i = 0; i < nodes.getLength(); i++) {
			Element document = (Element) nodes.item(i);
			String id = (String) idExpr.evaluate(document, XPathConstants.STRING);
			String num = (String) valueExpr.evaluate(document, XPathConstants.STRING);
			String descIt = "";
			String descEn = "";
			KeyValueItem item = facilities.get(id);
			if(item != null) {
				descIt = item.getDescIt();
				descEn = item.getDescEn();
			}
			
			Servizio.Builder servizio = Servizio.newBuilder();
			servizio.setId(id);
			servizio.setNumero(num);
			servizio.setDescIt(descIt);
			servizio.setDescEn(descEn);
			
			result.add(servizio.build());
		}
		return result;
	}

	private List<Foto> getFoto(Element service, XPathExpression fotoExpr, XPathExpression urlExpr,	
		XPathExpression descItExpr, XPathExpression descEnExpr) throws Exception {
		List<Foto> result = new ArrayList<Foto>();
		NodeList nodes = (NodeList) fotoExpr.evaluate(service, XPathConstants.NODESET);
		for (int i = 0; i < nodes.getLength(); i++) {
			Element document = (Element) nodes.item(i);
			String url = (String) urlExpr.evaluate(document, XPathConstants.STRING);
			String descit = (String) descItExpr.evaluate(document, XPathConstants.STRING);
			String descEn = (String) descEnExpr.evaluate(document, XPathConstants.STRING);
			Foto.Builder foto = Foto.newBuilder();
			foto.setImageUrl(url);
			foto.setLabelIt(descit);
			foto.setLabelEn(descEn);
			result.add(foto.build());
		}
		return result;
	}

	private Posizione getPosizione(Element service, XPathExpression latitudeExpr, XPathExpression longitudeExpr) throws Exception {
		String lat = (String) latitudeExpr.evaluate(service, XPathConstants.STRING);
		String lon = (String) longitudeExpr.evaluate(service, XPathConstants.STRING);
		if((lat != null) && (lon != null) && (!lat.isEmpty()) && (!lon.isEmpty())) {
			Posizione posizione = Posizione.newBuilder()
			.setLatitudine(new Double(lat))
			.setLongitudine(new Double(lon))
			.build();
			return posizione;
		}
		return null;
	}

	private String getIndirizzo(Element service, XPathExpression addressExpr, XPathExpression zipExpr, XPathExpression townExpr) throws Exception {
		String address = (String) addressExpr.evaluate(service, XPathConstants.STRING);
		String zip = (String) zipExpr.evaluate(service, XPathConstants.STRING);
		String town = (String) townExpr.evaluate(service, XPathConstants.STRING);
		return address + " - " + zip + " " + town;
	}

	private String getTipo(Element service, String categoryId) {
		String result = null;
		KeyValueItem item = categories.get(categoryId.toLowerCase());
		if(item != null) {
			result = item.getDescIt();
		}
		return result; 
	}
	
	private void setClassifications(Document keyvaluesDoc, XPathExpression idExpr, 
		XPathExpression translateItExpr, XPathExpression translateEnExpr) throws Exception {
		
		if(classifications == null) {
			classifications = new HashMap<String, KeyValueItem>();
			XPathExpression categoriesExpr = xpath.compile("/FeratelDsiRS/Result/KeyValues/Classifications/Classification");
			NodeList nodes = (NodeList) categoriesExpr.evaluate(keyvaluesDoc, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				Node singleNode = nodes.item(i);
				singleNode.getParentNode().removeChild(singleNode);
				Element facility = (Element) singleNode;
				String id = (String) idExpr.evaluate(facility, XPathConstants.STRING);
				String descIt = (String) translateItExpr.evaluate(facility, XPathConstants.STRING);
				String descEn = (String) translateEnExpr.evaluate(facility, XPathConstants.STRING);
				KeyValueItem item = new KeyValueItem(id, descIt, descEn);
				classifications.put(id, item);
			}
		}
	}
	
	private void setStars(Document keyvaluesDoc, XPathExpression idExpr, 
		XPathExpression translateItExpr, XPathExpression translateEnExpr) throws Exception {
		
		if(stars == null) {
			stars = new HashMap<String, KeyValueItem>();
			XPathExpression categoriesExpr = xpath.compile("/FeratelDsiRS/Result/KeyValues/Stars/Star");
			NodeList nodes = (NodeList) categoriesExpr.evaluate(keyvaluesDoc, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				Node singleNode = nodes.item(i);
				singleNode.getParentNode().removeChild(singleNode);
				Element facility = (Element) singleNode;
				String id = (String) idExpr.evaluate(facility, XPathConstants.STRING);
				String descIt = (String) translateItExpr.evaluate(facility, XPathConstants.STRING);
				String descEn = (String) translateEnExpr.evaluate(facility, XPathConstants.STRING);
				KeyValueItem item = new KeyValueItem(id, descIt, descEn);
				stars.put(id, item);
			}
		}
	}
	
	private void setFacilities(Document keyvaluesDoc, XPathExpression idExpr, 
		XPathExpression translateItExpr, XPathExpression translateEnExpr) throws Exception {
		
		if(facilities == null) {
			facilities = new HashMap<String, KeyValueItem>();
			XPathExpression categoriesExpr = xpath.compile("/FeratelDsiRS/Result/KeyValues/Facilities/Facility");
			NodeList nodes = (NodeList) categoriesExpr.evaluate(keyvaluesDoc, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				Node singleNode = nodes.item(i);
				singleNode.getParentNode().removeChild(singleNode);
				Element facility = (Element) singleNode;
				String id = (String) idExpr.evaluate(facility, XPathConstants.STRING);
				String descIt = (String) translateItExpr.evaluate(facility, XPathConstants.STRING);
				String descEn = (String) translateEnExpr.evaluate(facility, XPathConstants.STRING);
				KeyValueItem item = new KeyValueItem(id, descIt, descEn);
				facilities.put(id, item);
			}
		}
	}
	
	private void setCategories(Document keyvaluesDoc, XPathExpression idExpr, 
			XPathExpression translateItExpr, XPathExpression translateEnExpr) throws Exception {
		
		if(categories == null) {
			categories = new HashMap<String, KeyValueItem>();
			XPathExpression categoriesExpr = xpath.compile("/FeratelDsiRS/Result/KeyValues/Categories/Category");
			NodeList nodes = (NodeList) categoriesExpr.evaluate(keyvaluesDoc, XPathConstants.NODESET);
			for (int i = 0; i < nodes.getLength(); i++) {
				Node singleNode = nodes.item(i);
				singleNode.getParentNode().removeChild(singleNode);
				Element category = (Element) singleNode;
				String id = (String) idExpr.evaluate(category, XPathConstants.STRING);
				String descIt = (String) translateItExpr.evaluate(category, XPathConstants.STRING);
				String descEn = (String) translateEnExpr.evaluate(category, XPathConstants.STRING);
				KeyValueItem item = new KeyValueItem(id, descIt, descEn);
				categories.put(id, item);
			}
		}
	}
	
}
