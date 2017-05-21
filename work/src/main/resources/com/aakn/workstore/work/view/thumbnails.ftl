<#-- @ftlvariable name="" type="com.aakn.workstore.work.view.BaseView" -->
<#list works.works as work>
<div class="media">
    <div class="media-left media-middle">
        <a href="#">
            <img class="media-object" src="${work.images.smallImage}">
        </a>
    </div>
    <div class="media-body">
        <h4 class="media-heading">${work.exif.make?capitalize?html}</h4>
        Model: ${work.exif.model?html}
    </div>
</div>
</#list>