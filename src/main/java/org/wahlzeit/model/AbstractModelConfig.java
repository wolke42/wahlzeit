/*
 * Copyright (c) 2006-2009 by Dirk Riehle, http://dirkriehle.com
 *
 * This file is part of the Wahlzeit photo rating application.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */

package org.wahlzeit.model;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.utils.SystemProperty;
import org.wahlzeit.handlers.PartUtil;
import org.wahlzeit.main.ServiceMain;
import org.wahlzeit.services.AbstractConfig;
import org.wahlzeit.services.ConfigDir;
import org.wahlzeit.services.EmailAddress;
import org.wahlzeit.services.Language;
import org.wahlzeit.services.LogBuilder;
import org.wahlzeit.services.SysConfig;
import org.wahlzeit.utils.EnumValue;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

/**
 * A generic implementation of ModelConfig. Subclasses provide the parameters
 * and language-specific handling of text and data.
 */
public abstract class AbstractModelConfig extends AbstractConfig implements ModelConfig, Serializable {

	private static final Logger log = Logger.getLogger(AbstractModelConfig.class.getName());
	/**
	 *
	 */
	protected Language language = Language.ENGLISH;
	protected DateFormat dateFormatter = new SimpleDateFormat("MMM d, yyyy");
	protected DecimalFormat praiseFormatter = new DecimalFormat("##.#");

	/**
	 *
	 */
	protected AbstractModelConfig() {
		// do nothing
	}

	/**
	 *
	 */
	protected void initialize(Language myLanguage, DateFormat myDateFormatter, DecimalFormat myPraiseFormatter) {
		language = myLanguage;
		dateFormatter = myDateFormatter;
		praiseFormatter = myPraiseFormatter;

		try {
			ConfigDir templatesDir = SysConfig.getTemplatesDir();

			String shortDefaultFileName = myLanguage.asIsoCode() + File.separator + "ModelConfig.properties";
			if (templatesDir.hasDefaultFile(shortDefaultFileName)) {
				String absoluteDefaultFileName = templatesDir.getAbsoluteDefaultConfigFileName(shortDefaultFileName);
				loadProperties(absoluteDefaultFileName);
			}

			String shortCustomFileName = myLanguage.asIsoCode() + File.separator + "CustomModelConfig.properties";
			if (templatesDir.hasCustomFile(shortCustomFileName)) {
				String absoluteCustomFileName = templatesDir.getAbsoluteCustomConfigFileName(shortCustomFileName);
				loadProperties(absoluteCustomFileName);
			}
		} catch (IOException ioex) {
			log.warning(
					LogBuilder.createSystemMessage().addException("initializing directories failed", ioex).toString());
		}

		String menuDash = "&nbsp;" + doGetValue("MenuDash") + "&nbsp;";

		String footerCommunityPart = doGetValue("FooterCommunityPart");
		String footerAboutPart = doGetValue("FooterAboutPart");
		String footerLanguagePart = doGetValue("FooterLanguagePart");
		String footerPhotoSizePart0 = doGetValue("FooterPhotoSizePart0");
		String footerPhotoSizePart1 = doGetValue("FooterPhotoSizePart1");
		String footerPhotoSizePart2 = doGetValue("FooterPhotoSizePart2");
		String footerPhotoSizePart3 = doGetValue("FooterPhotoSizePart3");
		String footerPhotoSizePart4 = doGetValue("FooterPhotoSizePart4");

		boolean isInProduction = ServiceMain.getInstance().isInProduction();
		String footerDebugPart = !isInProduction ? menuDash + doGetValue("FooterDebugPart") : "";

		doSetValue("PageFooter0", footerCommunityPart + menuDash + footerAboutPart + menuDash + footerLanguagePart
				+ menuDash + footerPhotoSizePart0 + footerDebugPart);
		doSetValue("PageFooter1", footerCommunityPart + menuDash + footerAboutPart + menuDash + footerLanguagePart
				+ menuDash + footerPhotoSizePart1 + footerDebugPart);
		doSetValue("PageFooter2", footerCommunityPart + menuDash + footerAboutPart + menuDash + footerLanguagePart
				+ menuDash + footerPhotoSizePart2 + footerDebugPart);
		doSetValue("PageFooter3", footerCommunityPart + menuDash + footerAboutPart + menuDash + footerLanguagePart
				+ menuDash + footerPhotoSizePart3 + footerDebugPart);
		doSetValue("PageFooter4", footerCommunityPart + menuDash + footerAboutPart + menuDash + footerLanguagePart
				+ menuDash + footerPhotoSizePart4 + footerDebugPart);

		String baseMenu = doGetValue("BaseMenuPart");
		// there is no separate base menu

		String loginMenu = doGetValue("GuestMenuPart");
		UserService userService = UserServiceFactory.getUserService();
		loginMenu = loginMenu.replace("$loginPageLink$", userService.createLoginURL("/" + PartUtil.LOGIN_FORM_NAME));
		String guestMenu = baseMenu + menuDash + loginMenu;
		doSetValue("GuestMenu", guestMenu);

		String userMenu = doGetValue("UserMenuPart");
		userMenu = userMenu.replace("$logoutPageLink$", userService.createLogoutURL("/" + PartUtil.LOGOUT_PAGE_NAME));
		userMenu = baseMenu + menuDash + userMenu;
		doSetValue("UserMenu", userMenu);

		String moderatorMenu = userMenu + menuDash + doGetValue("ModeratorMenuPart");
		doSetValue("ModeratorMenu", moderatorMenu);

		String administratorMenu = moderatorMenu + menuDash + doGetValue("AdministratorMenuPart");
		doSetValue("AdministratorMenu", administratorMenu);
	}

	/**
	 *
	 */
	@Override
	public Language getLanguage() {
		return language;
	}

	/**
	 *
	 */
	@Override
	public String getLanguageCode() {
		return doGetValue("LanguageCode");
	}

	/**
	 *
	 */
	@Override
	public EmailAddress getModeratorEmailAddress() {
		return EmailAddress.getFromString(doGetValue("ModeratorEmailAddress"));
	}

	/**
	 *
	 */
	@Override
	public EmailAddress getAdministratorEmailAddress() {
		String appId = SystemProperty.applicationId.get();
		String localPart = doGetValue("AdministratorEmailAddressLocalPart");
		return EmailAddress.getFromString(localPart + "@" + appId + ".appspotmail.com");
	}

	/**
	 *
	 */
	@Override
	public EmailAddress getAuditEmailAddress() {
		return EmailAddress.getFromString(doGetValue("AuditEmailAddress"));
	}

	/**
	 *
	 */
	@Override
	public String getPageTitle() {
		return doGetValue("PageTitle");
	}

	/**
	 *
	 */
	@Override
	public String getPageHeading() {
		return doGetValue("PageHeading");
	}

	/**
	 *
	 */
	@Override
	public String getPageFooter(PhotoSize ss) {
		return doGetValue("PageFooter" + (ss.asInt() - 1));
	}

	/**
	 *
	 */
	@Override
	public String getPageMission() {
		return doGetValue("PageMission");
	}

	/**
	 *
	 */
	@Override
	public String getGuestMenu() {
		return doGetValue("GuestMenu");
	}

	/**
	 *
	 */
	@Override
	public String getUserMenu() {
		return doGetValue("UserMenu");
	}

	/**
	 *
	 */
	@Override
	public String getModeratorMenu() {
		return doGetValue("ModeratorMenu");
	}

	/**
	 *
	 */
	@Override
	public String getAdministratorMenu() {
		return doGetValue("AdministratorMenu");
	}

	/**
	 *
	 */
	@Override
	public String getCommunityMenu() {
		return doGetValue("CommunityMenu");
	}

	/**
	 *
	 */
	@Override
	public String getIllegalArgumentError() {
		return doGetValue("IllegalArgumentError");
	}

	/**
	 *
	 */
	@Override
	public String getIllegalAccessError() {
		return doGetValue("IllegalAccessError");
	}

	/**
	 *
	 */
	@Override
	public String getInternalProcessingError() {
		return doGetValue("InternalProcessingError");
	}

	/**
	 *
	 */
	@Override
	public String getFieldIsMissing() {
		return doGetValue("FieldIsMissing");
	}

	/**
	 *
	 */
	@Override
	public String getInputIsInvalid() {
		return doGetValue("InputIsInvalid");
	}

	/**
	 *
	 */
	@Override
	public String getInputIsTooLong() {
		return doGetValue("InputIsTooLong");
	}

	/**
	 *
	 */
	@Override
	public String getEmailAddressIsMissing() {
		return doGetValue("EmailAddressIsMissing");
	}

	/**
	 *
	 */
	@Override
	public String getEmailAddressIsInvalid() {
		return doGetValue("EmailAddressIsInvalid");
	}

	/**
	 *
	 */
	@Override
	public String getUrlIsInvalid() {
		return doGetValue("UrlIsInvalid");
	}

	/**
	 *
	 */
	@Override
	public String getKeepGoing() {
		return doGetValue("KeepGoing");
	}

	/**
	 *
	 */
	@Override
	public String getContinueWithTellFriends() {
		return doGetValue("ContinueWithTellFriends");
	}

	/**
	 *
	 */
	@Override
	public String getContinueWithShowPhoto() {
		return doGetValue("ContinueWithShowPhoto");
	}

	/**
	 *
	 */
	@Override
	public String getContinueWithShowUserHome() {
		return doGetValue("ContinueWithShowUserHome");
	}

	/**
	 *
	 */
	@Override
	public String getThankYou() {
		return doGetValue("ThankYou");
	}

	/**
	 *
	 */
	@Override
	public String getInformation() {
		return doGetValue("Information");
	}

	/**
	 *
	 */
	@Override
	public String getAnonUserName() {
		return doGetValue("AnonUserName");
	}

	/**
	 *
	 */
	@Override
	public String getResetSession() {
		return doGetValue("ResetSession");
	}

	/**
	 *
	 */
	@Override
	public String getEmailWasSent() {
		return doGetValue("EmailWasSent");
	}

	/**
	 *
	 */
	@Override
	public String getModeratorWasInformed() {
		return doGetValue("ModeratorWasInformed");
	}

	/**
	 *
	 */
	@Override
	public String getNeedToSignupFirst() {
		return doGetValue("NeedToSignupFirst");
	}

	/**
	 *
	 */
	@Override
	public String getOptionsWereSet() {
		return doGetValue("OptionsWereSet");
	}

	/**
	 *
	 */
	@Override
	public String getUserAlreadyExists() {
		return doGetValue("UserAlreadyExists");
	}

	/**
	 *
	 */
	@Override
	public String getUserNameIsReserved() {
		return doGetValue("UserNameIsReserved");
	}

	/**
	 *
	 */
	@Override
	public String getPasswordsDontMatch() {
		return doGetValue("PasswordsDontMatch");
	}

	/**
	 *
	 */
	@Override
	public String getDidntCheckTerms() {
		return doGetValue("DidntCheckTerms");
	}

	/**
	 *
	 */
	@Override
	public String getConfirmationEmailWasSent() {
		return doGetValue("ConfirmationEmailWasSent");
	}

	/**
	 *
	 */
	@Override
	public String getNeedToLoginFirst() {
		return doGetValue("NeedToLoginFirst");
	}

	/**
	 *
	 */
	@Override
	public String getConfirmAccountSucceeded() {
		return doGetValue("ConfirmAccountSucceeded");
	}

	/**
	 *
	 */
	@Override
	public String getConfirmAccountFailed() {
		return doGetValue("ConfirmAccountFailed");
	}

	/**
	 *
	 */
	@Override
	public String getLoginIsIncorrect() {
		return doGetValue("LoginIsIncorrect");
	}

	/**
	 *
	 */
	@Override
	public String getUserIsDisabled() {
		return doGetValue("UserIsDisabled");
	}

	/**
	 *
	 */
	@Override
	public String getUnknownEmailAddress() {
		return doGetValue("EmailAddressIsUnknown");
	}

	/**
	 *
	 */
	@Override
	public String getSendUserNameEmailSubject() {
		return doGetValue("EmailUserNameSubject");
	}

	/**
	 *
	 */
	@Override
	public String getUserNameWasEmailed() {
		return doGetValue("UserNameWasEmailed");
	}

	/**
	 *
	 */
	@Override
	public String getUserNameIsUnknown() {
		return doGetValue("UserNameIsUnknown");
	}

	/**
	 *
	 */
	@Override
	public String getSendPasswordEmailSubject() {
		return doGetValue("EmailPasswordSubject");
	}

	/**
	 *
	 */
	@Override
	public String getPasswordWasEmailed() {
		return doGetValue("PasswordWasEmailed");
	}

	/**
	 *
	 */
	@Override
	public String getNewLanguageSet() {
		return doGetValue("NewLanguageSet");
	}

	/**
	 *
	 */
	@Override
	public String getNoteMaximumPhotoSize() {
		return doGetValue("NoteMaximumPhotoSize");
	}

	/**
	 *
	 */
	@Override
	public String getNoPhotoUploaded() {
		return doGetValue("NoPhotoUploaded");
	}

	/**
	 *
	 */
	@Override
	public String getNoCharacterName() {
		return doGetValue("NoCharacterName");
	}

	/**
	 *
	 */
	@Override
	public String getNoSeriesName() {
		return doGetValue("NoSeriesName");
	}

	/**
	 *
	 */
	@Override
	public String getNoTags() {
		return doGetValue("NoTags");
	}

	/**
	 *
	 */
	@Override
	public String getProfileUpdateSucceeded() {
		return doGetValue("ProfileUpdateSucceeded");
	}

	/**
	 *
	 */
	@Override
	public String getPasswordChangeSucceeded() {
		return doGetValue("PasswordChangeSucceeded");
	}

	/**
	 *
	 */
	@Override
	public String getPhotoUpdateSucceeded() {
		return doGetValue("PhotoUpdateSucceeded");
	}

	/**
	 *
	 */
	@Override
	public String getPhotoUploadFailed() {
		return doGetValue("PhotoUploadFailed");
	}

	/**
	 *
	 */
	@Override
	public String getPhotoUploadSucceeded() {
		return doGetValue("PhotoUploadSucceeded");
	}

	/**
	 *
	 */
	@Override
	public String getLogoutSucceeded() {
		return doGetValue("LogoutSucceeded");
	}

	/**
	 *
	 */
	@Override
	public String getNoFlaggedPhotoCases() {
		return doGetValue("NoFlaggedPhotoCases");
	}

	/**
	 *
	 */
	@Override
	public String getPhotoIsUnknown() {
		return doGetValue("UnknownPhoto");
	}

	/**
	 *
	 */
	@Override
	public String getGeneralEmailRegards() {
		return doGetValue("GeneralEmailRegards");
	}

	/**
	 *
	 */
	@Override
	public String getGeneralEmailFooter() {
		return doGetValue("GeneralEmailFooter");
	}

	/**
	 *
	 */
	@Override
	public String getTellFriendEmailSubject() {
		return doGetValue("TellFriendEmailSubject");
	}

	/**
	 *
	 */
	@Override
	public String getTellFriendEmailWebsite() {
		return doGetValue("TellFriendEmailSite");
	}

	/**
	 *
	 */
	@Override
	public String getTellFriendEmailPhoto() {
		return doGetValue("TellFriendEmailPhoto");
	}

	/**
	 *
	 */
	@Override
	public String getSendEmailSubjectPrefix() {
		return doGetValue("SendEmailSubjectPrefix");
	}

	/**
	 *
	 */
	@Override
	public String getSendEmailBodyPrefix() {
		return doGetValue("SendEmailBodyPrefix");
	}

	/**
	 *
	 */
	@Override
	public String getSendEmailBodyPostfix() {
		return doGetValue("SendEmailBodyPostfix");
	}

	/**
	 *
	 */
	@Override
	public String getWelcomeEmailSubject() {
		return doGetValue("WelcomeEmailSubject");
	}

	/**
	 *
	 */
	@Override
	public String getWelcomeEmailBody() {
		return doGetValue("WelcomeEmailBody");
	}

	/**
	 *
	 */
	@Override
	public String getWelcomeEmailUserName() {
		return doGetValue("WelcomeEmailUserName");
	}

	/**
	 *
	 */
	@Override
	public String getNotifyAboutPraiseEmailSubject() {
		return doGetValue("NotifyAboutPraiseEmailSubject");
	}

	/**
	 *
	 */
	@Override
	public String getNotifyAboutPraiseEmailBody() {
		return doGetValue("NotifyAboutPraiseEmailBody");
	}

	/**
	 *
	 */
	@Override
	public String getNotifyAboutPraiseEmailPostScriptum() {
		return doGetValue("NotifyAboutPraiseEmailPostScriptum");
	}

	/**
	 *
	 */
	@Override
	public String asValueString(EnumValue ev) {
		return doGetValue(ev.getTypeName() + "#" + ev.asInt());
	}

	/**
	 *
	 */
	@Override
	public String asDateString(long millis) {
		return dateFormatter.format(millis);
	}

	/**
	 *
	 */
	@Override
	public String asPraiseString(double praise) {
		return praiseFormatter.format(praise);
	}

	/**
	 *
	 */
	public String getConfirmAccountEmailSubject() {
		return doGetValue("ConfirmAccountEmailSubject");
	}

	/**
	 *
	 */
	public String getConfirmAccountEmailBody() {
		return doGetValue("ConfirmAccountEmailBody");
	}

}
