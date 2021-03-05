/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import daos.HistoryDAO;
import dtos.AccountDTO;
import dtos.AnswersDTO;
import dtos.HistoryDTO;
import dtos.QuestionsDTO;
import java.io.IOException;
import java.io.PrintWriter;
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
public class CheckQuizController extends HttpServlet {
    private final String SUCCESS = "shome.jsp";
    private final String ERROR = "error.jsp";

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
            HttpSession session = request.getSession();
            List<QuestionsDTO> quizQuestion = (List<QuestionsDTO>) session.getAttribute("QUIZ");
            int rightAnswer = 0;
            String questionIdList = "";
            int subjectId = 0;
            for (QuestionsDTO question: quizQuestion) {
                questionIdList += question.getId() + ",";
                subjectId = question.getSubjectId();
                int choice = Integer.parseInt(request.getParameter("answer" + question.getId()));
                System.out.println("answer" + question.getId() + ":" + choice);
                for (AnswersDTO answer: question.getAnswers()) {
                    if (answer.getId() == choice && answer.isIsCorrect()) {
                        rightAnswer ++;
                    }
                }
            }
            AccountDTO account = (AccountDTO) session.getAttribute("ACCOUNT");
            HistoryDTO dto = new HistoryDTO(account.getAccountEmail(), questionIdList, subjectId, (10 / quizQuestion.size()) * rightAnswer);
            HistoryDAO hDao = new HistoryDAO();
            hDao.saveHistory(dto);
            request.setAttribute("RIGHT", rightAnswer);
            request.setAttribute("TOTAL", quizQuestion.size());
            session.removeAttribute("QUIZ");
            path = SUCCESS;
        } catch (Exception e) {
            request.setAttribute("ERROR", e.getMessage());
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
