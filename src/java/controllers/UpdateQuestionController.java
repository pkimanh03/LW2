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
public class UpdateQuestionController extends HttpServlet {

    private final String ERROR = "error.jsp";
    private final String SUCCESS = "SearchQuestionController?index=1";
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
        try {
            int questionId = Integer.parseInt(request.getParameter("questionId"));
            int subjectId = Integer.parseInt(request.getParameter("subject"));
            String questionContent = request.getParameter("questionContent");
            List<String> answers = new ArrayList();
            for (int i = 1; i <= 4; i++) {
                String answer = request.getParameter("answer" + i);
                answers.add(answer);
            }
            int correctAnswer = Integer.parseInt(request.getParameter("correctAnswer"));
            HttpSession session = request.getSession();
            AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNT");
            String actor = account.getAccountEmail();
            
            QuestionDAO dao = new QuestionDAO();
            boolean updatedQ = dao.updateQuestion(questionContent, subjectId, actor, questionId);
            if (updatedQ) {
                List<Integer> currentAnswers = dao.getAnswerIdsByQuestion(questionId);
                int count = 0;
                for(int i = 0; i < 4; i++) {
                    boolean updatedA = dao.updateAnswer(answers.get(i), (i+1) == correctAnswer, currentAnswers.get(i), actor);
                    if (updatedA) count ++;
                }
                if (count == answers.size()) {
                    request.setAttribute("MSG", "Your question is updated successfully!");
                    path = SUCCESS;
                }
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
