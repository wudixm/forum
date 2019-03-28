// Empty JS for your own code to be here
$(function(){
  $("#registera").on('click', function(){
    console.log('register is clicked');
    $.get("register.html",
      function(data){
        //这里是回调方法。返回data数据。这里想怎么处理就怎么处理了。
        console.log(data);
        $('.container-content').html(data);

      });
  });

});
