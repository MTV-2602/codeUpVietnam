<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Lỗi hệ thống - CodeUp Vietnam</title>
    <style>
      body {
        font-family: Arial, sans-serif;
        background: #f8d7da;
        color: #721c24;
      }
      .error-container {
        max-width: 500px;
        margin: 80px auto;
        background: #fff;
        border: 1px solid #f5c6cb;
        border-radius: 8px;
        padding: 32px 24px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
        text-align: center;
      }
      .error-title {
        font-size: 2em;
        margin-bottom: 16px;
      }
      .error-message {
        font-size: 1.2em;
        margin-bottom: 24px;
      }
      .btn-back {
        display: inline-block;
        padding: 8px 20px;
        background: #721c24;
        color: #fff;
        border: none;
        border-radius: 4px;
        text-decoration: none;
        font-size: 1em;
        cursor: pointer;
      }
      .btn-back:hover {
        background: #a71d2a;
      }
    </style>
  </head>
  <body>
    <div class="error-container">
      <div class="error-title">Đã xảy ra lỗi!</div>
      <div class="error-message">
        <% String error = (String) request.getAttribute("ERROR"); if (error !=
        null) { %> <%= error %> <% } else { %> Có lỗi không xác định xảy ra. Vui
        lòng thử lại sau! <% } %>
      </div>
      <a href="javascript:history.back()" class="btn-back">Quay lại</a>
      <a
        href="dashboard.jsp"
        class="btn-back"
        style="background: #6c757d; margin-left: 8px"
        >Về trang chính</a
      >
    </div>
  </body>
</html>
