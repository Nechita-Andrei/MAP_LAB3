package Repository;

import domain.Tema;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RepositoryTema extends AbstractRepository<Integer, Tema>{

    private String fisier;

    public RepositoryTema(String fisier) {

        this.fisier=fisier;
        //loadFromFile();
        loadXML();
    }

  /*  private void loadFromFile(){

        try(BufferedReader buffer = new BufferedReader(new FileReader(fisier))){
            String line;
            while ((line = buffer.readLine())!=null){
                String[] fields = line.split(",");
                if(fields.length!=4){
                    throw new Exception("Fisier corupt!");
                }
                try {

                    Tema t = new Tema(Integer.parseInt(fields[0]), fields[1],Integer.parseInt(fields[3]));
                    t.setStartWeek(Integer.parseInt(fields[2]));
                    DataValidator.getInstance().validareTema(t);
                    super.save(t);
                }
                catch (NumberFormatException e){
                    System.out.println("Date invalide!");
                }
            }
        }
        catch (FileNotFoundException e){
            System.out.println("Fisierul nu a fost gasit!");
        }
        catch (IOException e){
            System.out.println("Eroare la citire!");
        }
        catch (Exception e){e.printStackTrace();
        }
    }

    private void saveToFile(){
        try(PrintWriter p=new PrintWriter(fisier)){
            for(Tema t:findAll()){
                p.println(t.getId()+","+t.getDescriere()+","+t.getStartWeek()+","+t.getDeadlineWeek());

            }

        }
        catch(IOException e){
            System.out.println("eroare la scriere");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

   */

    private void loadXML(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try
        {
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(fisier);


            NodeList homeworkList = doc.getElementsByTagName("homework");

            for (int i=0 ; i< homeworkList.getLength(); i++)
            {
                Node node = homeworkList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element st = (Element) node;
                    String id = st.getAttribute("id");

                    NodeList nameList = st.getChildNodes();

                    List<String> attributes = new ArrayList<>();
                    for(int j=0 ; j<nameList.getLength() ; j++)
                    {
                        Node atr = nameList.item(j);
                        if(atr.getNodeType() == Node.ELEMENT_NODE){
                            Element name = (Element) atr;
                            attributes.add(name.getTextContent());
                        }
                    }

                    Tema tema = new Tema(Integer.parseInt(id), attributes.get(2),Integer.parseInt(attributes.get(1)));
                    tema.setStartWeek(Integer.parseInt(attributes.get(0)));
                    super.save(tema);
                }
            }

        }catch (ParserConfigurationException | SAXException | IOException e){
            System.out.println("Eroare la preluarea datelor ");;
        }
    }
    private void saveXML()
    {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try{

            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("homeworks");
            doc.appendChild(root);

            for (Tema tema: super.findAll())
            {
                Element homework = doc.createElement("homework");
                root.appendChild(homework);

                //Atribuim id-ul
                Attr id = doc.createAttribute("id");
                id.setValue(Integer.toString(tema.getId()));
                homework.setAttributeNode(id);

                //Atribuim celelalte atribute


                Element startWeek = doc.createElement("StartWeek");
                startWeek.appendChild(doc.createTextNode(Integer.toString(tema.getStartWeek())));
                homework.appendChild(startWeek);

                Element deadlineWeek = doc.createElement("deadlineWeek");
                deadlineWeek.appendChild(doc.createTextNode(Integer.toString(tema.getDeadlineWeek())));
                homework.appendChild(deadlineWeek);

                Element description = doc.createElement("descriere");
                description.appendChild(doc.createTextNode(tema.getDescriere()));
                homework.appendChild(description);




            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(fisier));

            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            transformer.transform(source,result);

        }catch (TransformerConfigurationException e){
            e.printStackTrace();
        }
        catch (TransformerException e){
            e.printStackTrace();
        }
        catch (ParserConfigurationException e){
            e.printStackTrace();
        }
    }

    public void saveTema(Tema e){
        super.save(e);
        saveXML();
    }

    public Tema update(Tema veche,Tema noua){
        noua=super.update(veche,noua);
        saveXML();
        return noua;
    }

    public Tema delete(Integer id){
        Tema tema=super.delete(id);
        saveXML();
        return tema;
    }
}