/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.watcher.actions.email;

import org.elasticsearch.common.logging.Loggers;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentParser;
import org.elasticsearch.xpack.common.text.TextTemplateEngine;
import org.elasticsearch.xpack.watcher.actions.ActionFactory;
import org.elasticsearch.xpack.notification.email.EmailService;
import org.elasticsearch.xpack.notification.email.HtmlSanitizer;
import org.elasticsearch.xpack.notification.email.attachment.EmailAttachmentsParser;

import java.io.IOException;

public class EmailActionFactory extends ActionFactory {

    private final EmailService emailService;
    private final TextTemplateEngine templateEngine;
    private final HtmlSanitizer htmlSanitizer;
    private final EmailAttachmentsParser emailAttachmentsParser;

    public EmailActionFactory(Settings settings, EmailService emailService, TextTemplateEngine templateEngine,
                              EmailAttachmentsParser emailAttachmentsParser) {
        super(Loggers.getLogger(ExecutableEmailAction.class, settings));
        this.emailService = emailService;
        this.templateEngine = templateEngine;
        this.htmlSanitizer = new HtmlSanitizer(settings);
        this.emailAttachmentsParser = emailAttachmentsParser;
    }

    @Override
    public ExecutableEmailAction parseExecutable(String watchId, String actionId, XContentParser parser) throws IOException {
        return new ExecutableEmailAction(EmailAction.parse(watchId, actionId, parser, emailAttachmentsParser),
                actionLogger, emailService, templateEngine, htmlSanitizer, emailAttachmentsParser.getParsers());
    }

}
