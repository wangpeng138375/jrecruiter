package com.hillert.upload.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.hillert.upload.service.UploadService;
import com.hillert.upload.web.ajax.AjaxFileUploadMultipartResolver;

/**
 *
 * @author Gunnar Hillert
 *
 */
public class UploadFilesController extends SimpleFormController {

    /**
     * Logger declaration.
     */
    private static final Log LOGGER =
                LogFactory.getLog(UploadFilesController.class);

    /**
     * Custom MultipartResolver - I am using it directly injected
     * into my controller instead of the global approach. That way
     * I can handle the MaxUploadSizeExceededException right here in the
     * controller class.
     */
    private AjaxFileUploadMultipartResolver multipartResolver;

    /**
     * Hold a reference to the service. Used e.g. to save the file.
     */
    private UploadService service;

    /**
     * Constructor.
     */
    public UploadFilesController() {
        super();
    }

    /**
     * @param multipartResolver The multipartResolver to set.
     */
    public final void setMultipartResolver(
                    final AjaxFileUploadMultipartResolver multipartResolver) {
        this.multipartResolver = multipartResolver;
    }

    /**
     * @param service The service to set.
     */
    public final void setService(final UploadService service) {
        this.service = service;
    }

    /**
     * Model and View.
     *
     * @param command Object
     * @param errors BindException
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     * @return ModelAndView
     */
    public final ModelAndView processFormSubmission(
                        final HttpServletRequest request,
                        final HttpServletResponse response,
                        final Object command,
                        final BindException errors)
    throws Exception {
        
    if (request.getParameter("cancel") != null) {
        LOGGER.info("Canceling operation.");
        return new ModelAndView(getSuccessView());
    }

    return super.processFormSubmission(request, response, command, errors);
    }

    /**
     * Register proerty editor for handling multipart files.
     *
     * @param request HttpServletRequest
     * @param binder ServletRequestDataBinder
     * @throws ServletException
     */
    protected void initBinder(
            final HttpServletRequest request,
            final ServletRequestDataBinder binder)
            throws ServletException {

 //           binder.registerCustomEditor(MultipartFile.class,
 //                                       new MultipartFilePropertyEditor());
    }

    /**
     * Spring-MVC method - executed after form submission.
     *
     * @param command Object
     * @param errors BindException
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception
     * @return ModelAndView
     */
    public final ModelAndView onSubmit(
                            final HttpServletRequest request,
                            final HttpServletResponse response,
                            final Object command,
                            final BindException errors)
            throws Exception {

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("entering 'onSubmit' method...");
        }

      //  final DataImportForm dataImportForm = (DataImportForm) command;

        MultipartHttpServletRequest multipartRequest = null;
        try {
            multipartRequest = multipartResolver.resolveMultipart(request);

        } catch (MaxUploadSizeExceededException e) {
 
            LOGGER.warn("Upload size exceeded!");
            errors.rejectValue("file", "errors.upload.size.exceeded",
                    null,
                    "bundle errors.upload.size.exceeded not found");
            LOGGER.warn(e); multipartRequest.getFileMap();
            return showForm(request, errors, getFormView());

        }

        final MultipartFile multipartFile =
            (MultipartFile) multipartRequest.getFile("file");

        if (multipartFile.getSize() == 0) {

            errors.rejectValue("file", "errors.upload.size.zero",
                    null,
                    "bundle errors.upload.size.zero not found");
            LOGGER.warn("User tried to upload a zero byte file.");

            return showForm(request, errors, getFormView());
        }

        service.saveFile(multipartFile.getInputStream(),
                         multipartFile.getOriginalFilename());

        request.getSession().setAttribute("message",
                getText("success.upload"));

        return new ModelAndView(getSuccessView());

    }

    /**
     * Convenience method for getting a i18n value for a given key.
     *
     * @param msgKey message bundle key
     * @return The value for a given message bundle key
     */
    public final String getText(final String msgKey) {
        return getMessageSourceAccessor().getMessage(msgKey);
    }

    /**
     * Store reference data.
     * @param request HttpServletRequest
     * @return The model map
     * @throws Exception
     */
    protected final Map referenceData(final HttpServletRequest request)
                throws Exception {
        final Map < Object, Object > model = new HashMap < Object, Object > ();
        final double max = multipartResolver.getFileUpload().getSizeMax();
        model.put("uploadSizeMax", max);
        return model;
    }

}
