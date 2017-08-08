package co.phystech.aosorio.controllers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.Borders;
//import org.apache.poi.xwpf.usermodel.LineSpacingRule;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFldChar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTJc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTString;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STJc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.phystech.aosorio.models.Book;
import co.phystech.aosorio.models.Comment;
import co.phystech.aosorio.models.NewFichePayload;

public class DocGenerator {

	private final static Logger slf4jLogger = LoggerFactory.getLogger(DocGenerator.class);

	private NewFichePayload fiche;

	private static XWPFParagraph[] parsHeader;
	private static XWPFParagraph[] parsFooter;

	/**
	 * @param fiche
	 */

	public DocGenerator() {

		this.fiche = new NewFichePayload();
		Book book = new Book();
		UUID bookUuid = UUID.fromString("cad346bd-0fd6-49d6-a633-67d6537c4bdd");
		book.setBook_uuid(bookUuid);
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
		comment.setAuthor("andres.osorio");
		comment.setAboutAuthor("Great author");
		comment.setAboutCharacters("Characters are fantastic! incredible lively and amusing");
		comment.setAboutGenre("Nothing much special about this genre");
		comment.setAboutCadre("New technologies, new authors");
		comment.setResume("This book tells us a story about passion and computing");
		comment.setExtrait("To be or not to be - \uc3b1");
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

		parsHeader = new XWPFParagraph[1];

		// ..Header & Footer
		CTP ctP = CTP.Factory.newInstance();
		CTText t = ctP.addNewR().addNewT();
		t.setStringValue("Fiche UUID: " + this.fiche.getBook().getBook_uuid().toString());
		parsHeader[0] = new XWPFParagraph(ctP, doc);

		XWPFHeaderFooterPolicy hfPolicy = doc.createHeaderFooterPolicy();
		hfPolicy.createHeader(XWPFHeaderFooterPolicy.DEFAULT, parsHeader);

		ctP = CTP.Factory.newInstance();
		t = ctP.addNewR().addNewT();
		t.setStringValue("Phystech | Date: " + new Date().toString());

		// ...Page number
		CTP ctpHeaderPage = CTP.Factory.newInstance();
		CTPPr ctppr = ctpHeaderPage.addNewPPr();
		CTString pst = ctppr.addNewPStyle();
		pst.setVal("style21");
		CTJc ctjc = ctppr.addNewJc();
		ctjc.setVal(STJc.RIGHT);
		ctppr.addNewRPr();
		CTR ctr = ctpHeaderPage.addNewR();
		ctr.addNewRPr();
		CTFldChar fch = ctr.addNewFldChar();
		fch.setFldCharType(STFldCharType.BEGIN);
		ctr = ctpHeaderPage.addNewR();
		ctr.addNewInstrText().setStringValue(" PAGE ");
		ctpHeaderPage.addNewR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);
		ctpHeaderPage.addNewR().addNewT().setStringValue("1");
		ctpHeaderPage.addNewR().addNewFldChar().setFldCharType(STFldCharType.END);

		parsFooter = new XWPFParagraph[2];
		parsFooter[0] = new XWPFParagraph(ctP, doc);
		parsFooter[0].setBorderTop(Borders.SINGLE);
		parsFooter[1] = new XWPFParagraph(ctpHeaderPage, doc);

		hfPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, parsFooter);

		// ...

		XWPFParagraph p1 = doc.createParagraph();
		p1.setAlignment(ParagraphAlignment.CENTER);
		p1.setBorderBottom(Borders.DOUBLE);
		p1.setBorderTop(Borders.DOUBLE);

		XWPFRun r1 = p1.createRun();
		r1.setBold(true);
		r1.setFontSize(14);
		r1.setText("Fiche de Lecture");
		r1.addCarriageReturn();
		
		XWPFParagraph p2 = doc.createParagraph();
		p2.setAlignment(ParagraphAlignment.LEFT);

		setBookParagraph(p2, "Title: ", fiche.getBook().getTitle());
		
		p2 = doc.createParagraph();
		setBookParagraph(p2, "Subtitle: ", fiche.getBook().getSubTitle());

		p2 = doc.createParagraph();
		setBookParagraph(p2, "Authors: ", fiche.getBook().getAuthor());
		
		p2 = doc.createParagraph();
		setBookParagraph(p2, "Year: ", String.valueOf(fiche.getBook().getYearPub()));
		
		p2 = doc.createParagraph();
		setBookParagraph(p2, "Editor: ", fiche.getBook().getEditor());
		
		p2 = doc.createParagraph();
		setBookParagraph(p2, "Collection: ", fiche.getBook().getCollection());

		p2 = doc.createParagraph();
		setBookParagraph(p2, "Pages: ", String.valueOf(fiche.getBook().getPages()));
		
		p2 = doc.createParagraph();
		setBookParagraph(p2, "Language: ", fiche.getBook().getLanguage());

		Iterator<Comment> itrComment = fiche.getComments().iterator();

		int counter = 1;

		slf4jLogger.info(String.valueOf(fiche.getComments().size()));

		while (itrComment.hasNext()) {

			slf4jLogger.info("Processing comment: " + String.valueOf(counter));

			Comment comment = itrComment.next();

			try {

				XWPFParagraph p4 = doc.createParagraph();
				p4.setBorderTop(Borders.SINGLE);
				p4.setBorderBottom(Borders.SINGLE);
				setParagraph(p4, "Reviewed by: ", comment.getAuthor());

				p4 = doc.createParagraph();
				setParagraph(p4, "About the author: ", comment.getAboutAuthor());

				p4 = doc.createParagraph();
				setParagraph(p4, "Genre: ", comment.getAboutGenre());

				p4 = doc.createParagraph();
				setParagraph(p4, "About the context: ", comment.getAboutCadre());

				p4 = doc.createParagraph();
				setParagraph(p4, "About the characters: ", comment.getAboutCharacters());

				p4 = doc.createParagraph();
				setParagraph(p4, "Summary: ", comment.getResume());

				p4 = doc.createParagraph();
				setParagraph(p4, "Extraits: ", comment.getExtrait());

				p4 = doc.createParagraph();
				setParagraph(p4, "Appreciation: ", comment.getAppreciation());

			} catch (NullPointerException ex) {

				slf4jLogger.info("There is a problem with comment");

			} catch (Exception ex) {

				ex.printStackTrace();
			}

			counter += 1;

		}

		FileOutputStream out;

		try {
			out = new FileOutputStream("fiche.docx");
			doc.write(out);
			out.close();
			doc.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void setBookParagraph(XWPFParagraph pParagraph, String pLabel, String pContent) {

		pParagraph.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun r3 = pParagraph.createRun();
		r3.setFontSize(12);
		r3.setBold(true);
		r3.setText(pLabel);
		r3 = pParagraph.createRun();
		r3.setBold(false);
		r3.setText(pContent);
		r3.addCarriageReturn();

	}

	private void setParagraph(XWPFParagraph pParagraph, String pLabel, String pContent) {

		pParagraph.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun r3 = pParagraph.createRun();
		r3.setFontSize(12);
		r3.setBold(true);
		r3.setText(pLabel);
		r3 = pParagraph.createRun();
		r3.setBold(false);
		r3.setText(pContent);
		r3.addCarriageReturn();

	}

}
