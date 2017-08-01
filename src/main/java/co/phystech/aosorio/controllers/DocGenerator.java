package co.phystech.aosorio.controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.xwpf.usermodel.Borders;
//import org.apache.poi.xwpf.usermodel.LineSpacingRule;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import co.phystech.aosorio.models.Book;
import co.phystech.aosorio.models.Comment;
import co.phystech.aosorio.models.NewFichePayload;

public class DocGenerator {
	
	private NewFichePayload fiche;
	
	/**
	 * @param fiche
	 */
	
	public DocGenerator() {
		
		this.fiche = new NewFichePayload();
		Book book = new Book();
		book.setTitle("My book title");
		book.setSubTitle("A great book on computing");
		book.setAuthor("McNabb, Andrew");
		book.setYearPub(2017);
		book.setEditor("Phystech Editions");
		book.setCollection("Computing collection");
		book.setPages(111);
		book.setLanguage("English");
		fiche.setBook(book);
	
		List<Comment> comments = new ArrayList<Comment>();
		Comment comment = new Comment();
		comment.setAboutAuthor("Great author");
		comment.setAboutGenre("Nothing much special about this genre");
		comment.setAboutCadre("New technologies, new authors");
		comment.setResume("This book tells us a story about passion and computing");
		comment.setExtrait("To be or not to be - ábéñ");
		comment.setAppreciation("Love this book, good work by McNabb");
		
		comments.add(comment);
		
		fiche.setComments(comments);
		
	}
	
	public DocGenerator(NewFichePayload fiche) {
		super();
		this.fiche = fiche;
	}



	public void generate() throws IOException {
		
		XWPFDocument doc = new XWPFDocument();

        XWPFParagraph p1 = doc.createParagraph();
        p1.setAlignment(ParagraphAlignment.CENTER);
        p1.setBorderBottom(Borders.DOUBLE);
        p1.setBorderTop(Borders.DOUBLE);
        p1.setBorderRight(Borders.DOUBLE);
        p1.setBorderLeft(Borders.DOUBLE);

        XWPFRun r1 = p1.createRun();
        r1.setBold(true);
        r1.setFontSize(14);
        r1.setText("Fiche de Lecture");

        XWPFParagraph p2 = doc.createParagraph();
        p2.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun r2 = p2.createRun();

        r2.setFontSize(12);
        
        r2.setBold(true);        
        r2.setText("Title: ");        
        r2.setText(fiche.getBook().getTitle());
        r2.addCarriageReturn();
        
        r2.setText("Subtitle: " + fiche.getBook().getSubTitle());
        r2.addCarriageReturn();

        r2.setText("Authors: " + fiche.getBook().getAuthor());
        r2.addCarriageReturn();
        
        r2.setText("Year: " + fiche.getBook().getYearPub());
        r2.addCarriageReturn();

        r2.setText("Editor: " + fiche.getBook().getEditor());
        r2.addCarriageReturn();

        r2.setText("Collection: " + fiche.getBook().getCollection());
        r2.addCarriageReturn();

        r2.setText("Pages: " + fiche.getBook().getPages());
        r2.setText("Language: " + fiche.getBook().getLanguage());
        r2.addCarriageReturn();

        Iterator<Comment> itrComment = fiche.getComments().iterator();
        
        while(itrComment.hasNext()) {
        	
        	Comment comment = itrComment.next();
        	      
        	XWPFParagraph p3 = doc.createParagraph();
            p3.setAlignment(ParagraphAlignment.LEFT);

            XWPFRun r3 = p3.createRun();
            r3.setFontSize(12);
  
            r3.setText("Author: " + comment.getAboutAuthor());
            r3.addCarriageReturn();
            
        	r3.setText("Genre: ");
        	r3.addCarriageReturn();
        	
        	r3.setText("Context: ");
        	r3.addCarriageReturn();
        	
        	r3.setText("Characters: ");
        	r3.addCarriageReturn();
        	
        	r3.setText("Resumé: ");
        	r3.addCarriageReturn();
        	
        	r3.setText("Extraits: ");
        	r3.addCarriageReturn();
        	
        	r3.setText("Appreciation: ");
        	r3.addCarriageReturn();
   	
        	
   
        }
        
        
        

        FileOutputStream out;
        
		try {
			out = new FileOutputStream("fiche.docx");
			doc.write(out);
			out.close();
			doc.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
	
}
