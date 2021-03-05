/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.QuestionDAO;
import dtos.AccountDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author pkimanh03
 */
public class CreateQuestionController extends HttpServlet {

    private final String ERROR = "error.jsp";
    private final String SUCCESS = "ahome.jsp";
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String path = ERROR;
        System.out.println("CreateQuestionController");
        try {
//            String question, int subject, String actor, List<String> answers, int correctAnswer
            String question = request.getParameter("question");
            int subject = Integer.parseInt(request.getParameter("subject"));
            HttpSession session = request.getSession();
            AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNT");
            String actor = account.getAccountEmail();
            List<String> answers = new ArrayList();
            for (int i = 1; i <= 4; i++) {
                String answer = request.getParameter("answer" + i);
                answers.add(answer);
            }
            int correctAnswer = Integer.parseInt(request.getParameter("correctAnswer"));
            
            QuestionDAO dao = new QuestionDAO();
            long createdTime = dao.insertQuestion(question, subject, actor);
            int questionId = dao.getQuestionAfterCreate(createdTime, actor);
            if (questionId > 0) {
                int count = 0;
                for (int i = 0; i < answers.size(); i++) {
                    boolean insertedA = dao.insertAnswers(questionId, actor, answers.get(i), (i+1) == correctAnswer);
                    if (insertedA) count ++; 
                }
                if (count == answers.size()) {
                    request.setAttribute("MSG", "Your question is inserted successfully.");
                    path = SUCCESS;
                }
            } else {
                request.setAttribute("MSG", "Sorry! We cannot insert your question");
            }
        } catch (Exception e) {
            request.setAttribute("ERROR", e.getMessage());
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(path).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
