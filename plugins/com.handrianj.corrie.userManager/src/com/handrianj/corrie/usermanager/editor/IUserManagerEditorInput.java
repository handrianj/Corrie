package com.handrianj.corrie.usermanager.editor;

import java.util.List;

import com.handrianj.corrie.dblink.model.IUser;
import com.handrianj.corrie.editors.util.editors.ICorrieEditorInput;

/**
 * Prototype for the user manager editor input
 *
 * @author Heri Andrianjafy
 *
 * @param <U>
 */
public interface IUserManagerEditorInput<U extends IUser> extends ICorrieEditorInput<List<U>> {

}
