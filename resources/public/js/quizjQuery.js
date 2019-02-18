$(function(){
  // var init_question_strs = ['你发现在向别人做自我介绍时有困难。','你经常陷入沉思，忽视或忘记了周围。','你总想尽快回复电子邮件，无法忍受杂乱的收件箱。','你发现，即便有些压力，你也能轻易保持放松和专注。','你通常不会主动跟人交谈。','你很少出于纯粹的好奇心做什么事。','你觉得自己比别人优越。','对你来说，有条理比能适应更重要。','你通常都很有动力和活力。','对你来说，不让别人感到不愉快比赢得辩论更重要。','你经常觉得必须向别人解释你行为的理由。','你的家和工作环境都很整洁。','你不介意成为别人注意的中心。','你认为自己更现实，而不是更有创造力。','人们很少令你不快。','你的旅行计划通常经过深思熟虑。','你常常很难感受到别人的感受。','你的情绪变化很快。','在与人讨论时，你更看重事实，而不是人们敏感的情绪。','你很少担心你的行为对别人有什么影响。','你的工作风格遵循不定期出现的能量高峰，而不是采用有系统有组织的方法。','你经常嫉妒别人。','有趣的书或电子游戏常比社交活动更有吸引力。','每个项目最关键的是能够制定和遵守计划。','你很少沉溺于幻想或想法。','在自然环境中散步时，你常发现自己陷入沉思。','如果有人没迅速回复你的电子邮件，你会担心是不是自己说错了话。','为人父母，你更愿意看到孩子成为善良的人，而不是聪明人。','你不让你的行为受别人影响。','你的梦常是有关真实世界和事件的。','在新的工作单位，你没多久就开始参加社交活动。','你更多是天生的即兴表现者，而不是周密计划者。','你更多受情绪控制，而不是控制情绪。','你喜欢参加需盛装或化妆出席的社交活动。','你经常花时间探索不现实、不可行，但会触发灵感的想法。','你更愿意即兴发挥，而不是花时间制定详细的计划。','你是个相对拘谨、安静的人。','如果你是企业主，你会觉得解雇忠诚但绩效不佳的员工是件难事。','你经常思考人类存在的意义。','在做重要决定时，逻辑通常比心愿更重要。','保持开放的选择比列出行动计划更重要。','如果你的朋友为什么事伤心，你更可能在情感上支持他，而不是向他建议处理问题的办法。','你很少有不安全感。','在制定个人时间表并坚持执行这方面，你没什么困难。','在团队工作中，正确做事比合作态度更重要。','你认为，每个人的看法都应得到尊重，无论是否有事实根据。','在与一群人共度时光之后，你感到更有活力。','你经常把东西放错地方。','你认为自己情绪很稳定。','你的头脑常塞满未经探索的想法和计划。','你不会叫自己梦想家。','面对很多人发言，你通常觉得难以放松。','一般说来，你更多依赖自己的经验而非想象力。','你过于担心人们怎么想。','如果房间里有很多人，你会靠近墙边，避免处于中心位置。','你总爱拖延，直到没有足够的时间做每件事。','你在面对压力情境时感到很焦虑。','你认为，自己被别人喜欢比拥有权势更值得。','你总是对非常规和含义不明的东西感兴趣，比如书籍、艺术或电影。','在社交场合，你经常很主动。'];
  // var init_question_strs = ['你发现在向别人做自我介绍时有困难。','你经常陷入沉思，忽视或忘记了周围。','你总想尽快回复电子邮件，无法忍受杂乱的收件箱。','你更愿意即兴发挥，而不是花时间制定详细的计划。','你是个相对拘谨、安静的人。','如果你是企业主，你会觉得解雇忠诚但绩效不佳的员工是件难事。'];
  var init_question_strs = ['你发现在向别人做自我介绍时有困难。','你经常陷入沉思，忽视或忘记了周围。','你总想尽快回复电子邮件，无法忍受杂乱的收件箱。'];
  var questions = [];
  for (var i = 0, len = init_question_strs.length; i < len; i++) {
    var item = {"id":i, "question":init_question_strs[i]};
    questions.push(item);
  }
  // var questions = [
  // {
  // "id":1,
  // "question":"你发现在向别人做自我介绍时有困难。",
  // },{
  // "id":2,
  // "question":"你经常陷入沉思，忽视或忘记了周围。"
  // }
  // ];
  var radios = [
    {"value":0, "text":"非常同意"},
    {"value":1, "text":"还算同意"},
    {"value":2, "text":"中立"},
    {"value":3, "text":"稍不同意"},
    {"value":4, "text":"非常不同意"},
  ];
  var $question = $('#quiz');
  var $qtTemplate = $('#qt_template_js').html();
  var $radioTemplate = $('#qt_radio_template_js').html();
  var $wholeQuestion = $('#qt_whole_template_js').html();
  $.each(questions, function(index, question){
    var question_str = Mustache.render($qtTemplate, question);
    var options = [];
    $.each(radios, function(radio_index, radio_item){
      var this_item = {"radio_name":question.id, "value":radio_item.value, "text":radio_item.text};
      options.push(Mustache.render($radioTemplate, this_item));
    });
    var whole_q = {"question_str":question_str, "options": options.join("")};
    console.log(whole_q);
    $question.append(Mustache.render($wholeQuestion, whole_q));
  });

  var $slides = $(".slide");
  var $preBtn = $("#previous");
  var $nextBtn = $("#next");
  var $submitBtn = $("#submit");

  var currentSlide = 0;

  function showSlide(n){
    $($slides[currentSlide]).removeClass("active-slide");
    $($slides[n]).addClass("active-slide");
    currentSlide = n;

    if (currentSlide == 0) {
      // 前面按钮不显示
      $preBtn.hide(500);
    } else {
      $preBtn.show(500);
    }
    if(currentSlide === $slides.length - 1) {
      $nextBtn.hide(500);
      $submitBtn.show(500);
    } else {
      $nextBtn.show(500);
      $submitBtn.hide(500);
    }
  };
  showSlide(0);
  function showNextSlide(){
    showSlide(currentSlide + 1);
  };
  function showPreviousSlide(){
    showSlide(currentSlide - 1);
  };
  $nextBtn.on('click', function(){
    console.log('next btn is clicked');
    showNextSlide();
  });
  $preBtn.on('click',function(){
    console.log('previous btn is clicked');
    showPreviousSlide();
  });
  $question.on('click',function(){
    console.log('question btn is clicked');
  });
  $submitBtn.on('click',function(){
    // 校验所有的slide 里面的单选框是否已经选过了
    // 向后台发送数据
    // 显示后台数据

    req_data = [];
    for (var each in questions) {
      console.log(each);
      var $select_radio = $("input[name='"+each+"']:checked");
      if ($select_radio.val() === undefined) {
        var not_select = parseInt(each) + 1;
        alert(not_select + 'not select');
        return;
      }
      // var data = {"question_id":each, "radio_val":$select_radio.val()};
      var data = $select_radio.val();
      req_data.push(data);
      console.log(req_data);

    }
    $.post('/finish', JSON.stringify(req_data),
      function(returnedData){
        console.log(returnedData);
      }, 'json');
    // $.ajax({
      // url: '/finish',
      // type: 'POST',
      // contentType : 'application/json',
      // data: JSON.stringify(req_data),
      // complete: function (jqXHR, textStatus) {
        // console.log("complete");
        // // callback
      // },
      // success: function (data, textStatus, jqXHR) {
        // console.log("success");
        // // success callback
      // },
      // error: function (jqXHR, textStatus, errorThrown) {
        // console.log("error");
        // // error callback
      // }
    // });

  });

});

