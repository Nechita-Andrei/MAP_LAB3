package Repository;

import domain.Student;
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

public class RepositoryStudents extends AbstractRepository<Integer,Student>{

    private String fisier;
    public RepositoryStudents(String fisier){
        this.fisier = fisier;
        //loadFromFile();
        loadXML();
    }
/*
    private void loadFromFile(){

        try(BufferedReader buffer = new BufferedReader(new FileReader(fisier))){
            String line;
            while ((line = buffer.readLine())!=null){
                String[] fields = line.split(",");
                if(fields.length!=6){
                    throw new Exception("Fisier corupt!");
                }
                try {
                    Student s = new Student(Integer.parseInt(fields[0]), fields[1], fields[2], Integer.parseInt(fields[3]), fields[4], fields[5]);
                    DataValidator.getInstance().validareStudent(s);
                    super.save(s);
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

    private void saveToFile()  {
        try(PrintWriter p=new PrintWriter(fisier)){
            for(Student s:findAll()){
                p.println(s.getId()+","+s.getNume()+","+s.getPrenume()+","+s.getGrupa()+","+s.getEmail()+","+s.getCadruDidacticIndrumatorLab());

            }

        }
        catch (IOException e){
            System.out.println("eroare la scriere");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

 */
    private void loadXML(){
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder builder=factory.newDocumentBuilder();
            Document doc=builder.parse(fisier);
            NodeList studentList=doc.getElementsByTagName("student");
            for(int i=0;i<studentList.getLength();i++){
                Node nod=studentList.item(i);
                if(nod.getNodeType()==Node.ELEMENT_NODE){
                    Element st=(Element) nod;
                    String id=st.getAttribute("Id");

                    NodeList nameList=st.getChildNodes();
                    List<String> attributes=new ArrayList<>();
                    for(int j=0;j<nameList.getLength();j++){
                        Node atr=nameList.item(j);
                        if(atr.getNodeType()==Node.ELEMENT_NODE){
                            Element name=(Element) atr;
                            attributes.add(name.getTextContent());
                        }
                    }
                    Student student=new Student(Integer.parseInt(id),attributes.get(0),attributes.get(1),Integer.parseInt(attributes.get(2)),attributes.get(3),attributes.get(4));
                    super.save(student);
                }

            }
        }
        catch (ParserConfigurationException| SAXException|IOException e){
            System.out.println("Eroare la preluarea datelor");
        }
    }

    private void saveXML(){
        DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder builder=documentBuilderFactory.newDocumentBuilder();
            Document doc=builder.newDocument();

            Element root=doc.createElement("Studenti");
            doc.appendChild(root);
            for(Student s:super.findAll()){
                Element student=doc.createElement("student");
                root.appendChild(student);

                Attr id=doc.createAttribute("Id");
                id.setValue(Integer.toString(s.getId()));
                student.setAttributeNode(id);

                Element nume=doc.createElement("Numele");
                nume.appendChild(doc.createTextNode(s.getNume()));
                student.appendChild(nume);

                Element prenume=doc.createElement("Prenumele");
                prenume.appendChild(doc.createTextNode(s.getPrenume()));
                student.appendChild(prenume);

                Element grupa=doc.createElement("grupa");
                grupa.appendChild(doc.createTextNode(Integer.toString(s.getGrupa())));
                student.appendChild(grupa);

                Element email=doc.createElement("email");
                email.appendChild(doc.createTextNode(s.getEmail()));
                student.appendChild(email);

                Element profesor=doc.createElement("Profesor");
                profesor.appendChild(doc.createTextNode(s.getCadruDidacticIndrumatorLab()));
                student.appendChild(profesor);
            }
            TransformerFactory transformerFactory=TransformerFactory.newInstance();
            Transformer transformer=transformerFactory.newTransformer();
            DOMSource source=new DOMSource(doc);
            StreamResult result=new StreamResult(new File(fisier));

            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            transformer.transform(source,result);
        }
        catch (TransformerConfigurationException e){
            e.printStackTrace();
        }
        catch (TransformerException e){
            e.printStackTrace();
        }
        catch (ParserConfigurationException e){
            e.printStackTrace();
        }
    }

    public void saveS(Student e){
        super.save(e);
        saveXML();

    }

    public Student update(Student vechi,Student nou){
        nou=super.update(vechi,nou);
        saveXML();
        return nou;
    }

    public Student delete(Integer id){
        Student s=super.delete(id);
        saveXML();
        return s;
    }
}
