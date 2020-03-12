package Repository;

import domain.Nota;
import domain.Student;
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

public class RepositoryNota extends AbstractRepository<Integer, Nota> {
    private String filename;

    public RepositoryNota(String filename){

        this.filename=filename;
        loadNotaXML();
    }

    /*private void saveToFile(){
        try(PrintWriter p=new PrintWriter(filename)){
            for(Nota n:findAll()){
                p.println(n.toString());
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

    private void saveXML(){
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try{

            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("Grades");
            doc.appendChild(root);

            for (Nota nota: super.findAll())
            {
                Element Grade = doc.createElement("grade");
                root.appendChild(Grade);

                //Atribuim id-ul
                Attr id = doc.createAttribute("id");
                id.setValue(Integer.toString(nota.getId()));
                Grade.setAttributeNode(id);

                //Atribuim studentul


                Element student = doc.createElement("student");
                Attr idStudent = doc.createAttribute("id");
                idStudent.setValue(Integer.toString(nota.getStudent().getId()));
                student.setAttributeNode(idStudent);

                //Atribuim datele studentului
                Element nume = doc.createElement("firstName");
                nume.appendChild(doc.createTextNode(nota.getStudent().getNume()));
                student.appendChild(nume);

                Element prenume = doc.createElement("lastName");
                prenume.appendChild(doc.createTextNode(nota.getStudent().getPrenume()));
                student.appendChild(prenume);

                Element group = doc.createElement("group");
                group.appendChild(doc.createTextNode(Integer.toString(nota.getStudent().getGrupa())));
                student.appendChild(group);

                Element email = doc.createElement("email");
                email.appendChild(doc.createTextNode(nota.getStudent().getEmail()));
                student.appendChild(email);

                Element teacher= doc.createElement("teacher");
                teacher.appendChild(doc.createTextNode(nota.getStudent().getCadruDidacticIndrumatorLab()));
                student.appendChild(teacher);

                Grade.appendChild(student);


                //Atribuim tema

                Element tema = doc.createElement("homework");
                Attr idTema = doc.createAttribute("id");
                idTema.setValue(Integer.toString(nota.getTema().getId()));
                tema.setAttributeNode(idTema);

                //Atribuim datele temei
                Element startWeek = doc.createElement("StartWeek");
                startWeek.appendChild(doc.createTextNode(Integer.toString(nota.getTema().getStartWeek())));
                tema.appendChild(startWeek);

                Element deadlineWeek = doc.createElement("deadlineWeek");
                deadlineWeek.appendChild(doc.createTextNode(Integer.toString(nota.getTema().getDeadlineWeek())));
                tema.appendChild(deadlineWeek);

                Element description = doc.createElement("description");
                description.appendChild(doc.createTextNode(nota.getTema().getDescriere()));
                tema.appendChild(description);


                Grade.appendChild(tema);


                //Atribuim restul elementelor


                Element teacherNota = doc.createElement("teacherNota");
                teacherNota.appendChild(doc.createTextNode(nota.getProfesor()));
                Grade.appendChild(teacherNota);

                Element isLate = doc.createElement("isLate");
                isLate.appendChild(doc.createTextNode(Integer.toString(nota.getIntarziere())));
                Grade.appendChild(isLate);

                Element feedkback = doc.createElement("Feedback");
                feedkback.appendChild(doc.createTextNode(nota.getFeedback()));
                Grade.appendChild(feedkback);

                Element dataPredarii = doc.createElement("dataPredarii");
                dataPredarii.appendChild(doc.createTextNode(Integer.toString(nota.getDataP())));
                Grade.appendChild(dataPredarii);

                Element firstGrade = doc.createElement("firstGrade");
                firstGrade.appendChild(doc.createTextNode(Integer.toString(nota.getNotaInitiala())));
                Grade.appendChild(firstGrade);

                Element finalGrade = doc.createElement("finalGrade");
                finalGrade.appendChild(doc.createTextNode(Integer.toString(nota.CalculateFinalGrade())));
                Grade.appendChild(finalGrade);


            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filename));

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
        }finally {

        }
    }
    private void loadNotaXML()
    {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try
        {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(filename);
            NodeList gradeList = doc.getElementsByTagName("grade");

            for (int i=0 ; i< gradeList.getLength(); i++)
            {
                Node node = gradeList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element grade = (Element) node;
                    String id = grade.getAttribute("id");

                    NodeList nameList = grade.getChildNodes();

                    Node studentNode = nameList.item(1);



                    Element stElem,tmElem;
                    String idStudent="0",idTema="0";
                    List<String> studentStringList = new ArrayList<>();
                    List<String> temaStringList = new ArrayList<>();

                    if(studentNode!=null && studentNode.getNodeType() == Node.ELEMENT_NODE){

                        stElem = (Element) studentNode;
                        idStudent = stElem.getAttribute("id");

                        NodeList stAttrList = stElem.getChildNodes();


                        for (int k=0;k<stAttrList.getLength();k++)
                        {
                            Node node1 = stAttrList.item(k);
                            if(node1.getNodeType() == Node.ELEMENT_NODE){
                                Element attrib = (Element) node1;
                                studentStringList.add(attrib.getTextContent());
                            }
                        }


                    }
                    Student student1 = new Student(Integer.parseInt(idStudent),
                            studentStringList.get(0),studentStringList.get(1),Integer.parseInt(studentStringList.get(2)),
                            studentStringList.get(3),studentStringList.get(4));

                    Node temaNode = nameList.item(3);

                    if(temaNode!=null && temaNode.getNodeType()==Node.ELEMENT_NODE){
                        tmElem = (Element) temaNode;
                        idTema = tmElem.getAttribute("id");
                        NodeList tmAttrList = tmElem.getChildNodes();

                        for (int z=0;z<tmAttrList.getLength();z++)
                        {
                            Node node1 = tmAttrList.item(z);
                            if(node1.getNodeType() == Node.ELEMENT_NODE){
                                Element attrib = (Element) node1;
                                temaStringList.add(attrib.getTextContent());
                            }
                        }

                    }


                    Tema tema1 = new Tema(Integer.parseInt(idTema),temaStringList.get(2),Integer.parseInt(temaStringList.get(1)));
                    tema1.setStartWeek(Integer.parseInt(temaStringList.get(0)));
                    List<String> attributes = new ArrayList<>();

                    for(int j=4 ; j<nameList.getLength() ; j++)
                    {
                        Node atr = nameList.item(j);
                        if(atr.getNodeType() == Node.ELEMENT_NODE){
                            Element name = (Element) atr;
                            attributes.add(name.getTextContent());
                        }
                    }

                    Nota nota = new Nota(Integer.parseInt(id),student1,tema1,attributes.get(2),Integer.parseInt(attributes.get(4)));
                    nota.setIntarziere(Integer.parseInt(attributes.get(1)));
                    nota.setDataP(Integer.parseInt(attributes.get(3)));
                    nota.setNotaFinala(nota.CalculateFinalGrade());
                    super.save(nota);
                }
            }

        }catch (ParserConfigurationException | SAXException | IOException e){
            System.out.println("Eroare la preluarea datelor ");;
        }
    }


    @Override
    public void save(Nota e){
        super.save(e);
        saveXML();
    }

    public Nota update(Nota veche,Nota noua){
        noua=super.update(veche, noua);
        saveXML();
        return  noua;
    }

    public Nota delete(Integer id){
        Nota n=super.delete(id);
        saveXML();
        return n;
    }

}
