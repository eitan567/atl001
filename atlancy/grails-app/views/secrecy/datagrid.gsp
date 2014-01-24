<section class="panel">
  <header class="panel-heading">
    DataGrid 
    <i class="icon-info-sign text-muted" data-toggle="tooltip" data-placement="bottom" data-title="ajax to load the data."></i> 
  </header>
  <div class="table-responsive">
    <table id="MyStretchGrid" class="table table-striped datagrid m-b-sm">
      <thead>
        <tr>
          <th>
            <div class="row">
              <div class="col-sm-8 m-t-xs m-b-xs">
                <div class="select filter" data-resize="auto">
                  <button data-toggle="dropdown" class="btn btn-sm btn-white dropdown-toggle">
                    <span class="dropdown-label"></span>
                    <span class="caret"></span>
                  </button>
                  <ul class="dropdown-menu">
                    <li data-value="all" data-selected="true"><a href="#">All</a></li>
                    <li data-value="lt5m"><a href="#">Population &lt; 5M</a></li>
                    <li data-value="gte5m"><a href="#">Population &gt;= 5M</a></li>
                  </ul>
                </div>
              </div>
              <div class="col-sm-4 m-t-xs m-b-xs">
                <div class="input-group search datagrid-search">
                  <input type="text" class="input-sm form-control" placeholder="Search">
                  <div class="input-group-btn">
                    <button class="btn btn-white btn-sm"><i class="icon-search"></i></button>
                  </div>
                </div>
              </div>
            </div>
          </th>
        </tr>
      </thead>

      <tfoot>
        <tr>
          <th class="row">
            <div class="datagrid-footer-right col-sm-6 text-center-xs m-r-n" style="display:none;">
              <div class="grid-controls m-t-sm">
                <span>
                  <span class="grid-start"></span> -
                  <span class="grid-end"></span> of
                  <span class="grid-count"></span>
                </span>
                <div class="select grid-pagesize dropup" data-resize="auto">
                  <button data-toggle="dropdown" class="btn btn-sm btn-white dropdown-toggle">
                    <span class="dropdown-label"></span>
                    <span class="caret"></span>
                  </button>
                  <ul class="dropdown-menu">
                    <li data-value="5"><a href="#">5</a></li>
                    <li data-value="10"><a href="#">10</a></li>
                    <li data-value="20" data-selected="true"><a href="#">20</a></li>
                    <li data-value="50"><a href="#">50</a></li>
                    <li data-value="100"><a href="#">100</a></li>
                  </ul>
                </div>
                <span>Per Page</span>
              </div>
            </div>
            <div class="datagrid-footer-left col-sm-6 text-left text-center-xs" style="display:none;">
              <div class="grid-pager m-l-n">
                <button type="button" class="btn btn-sm btn-white grid-prevpage"><i class="icon-chevron-right"></i></button>
                <span>Page</span>
                <div class="inline">
                  <div class="input-group dropdown combobox">
                    <input class="input-sm form-control" type="text">
                    <div class="input-group-btn dropup">
                      <button class="btn btn-sm btn-white" data-toggle="dropdown"><i class="caret"></i></button>
                      <ul class="dropdown-menu pull-left"></ul>
                    </div>
                  </div>
                </div>
                <span>of <span class="grid-pages"></span></span>
                <button type="button" class="btn btn-sm btn-white grid-nextpage"><i class="icon-chevron-left"></i></button>
              </div>
            </div>
          </th>
        </tr>
      </tfoot>
    </table>
  </div>
</section>