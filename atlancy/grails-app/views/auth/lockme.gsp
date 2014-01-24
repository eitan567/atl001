<div class="modal-over">
  <div class="modal-center animated flipInX" style="width:300px;margin:-30px -150px 0 0;">
    <div class="pull-right thumb m-l"><img src="${resource(dir: 'images', file: 'avatar.jpg')}" class="img-thumbnail"/></div>
    <div class="clear">
      <p class="text-white">${user.firstName + " " + user.lastName}</p>
      <div class="input-group input-s">
        <input type="password" name="password" placeholder="סיסמה" class="form-control text-sm">        
        <span class="input-group-btn">
          <button class="btn btn-success" type="button" data-dismiss="modal"><i class="icon-arrow-left"></i></button>
        </span>
      </div>
    </div>
  </div>
</div>