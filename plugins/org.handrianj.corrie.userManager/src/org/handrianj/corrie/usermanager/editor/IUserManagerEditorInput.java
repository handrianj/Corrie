package org.handrianj.corrie.usermanager.editor;

import java.util.List;

import org.handrianj.corrie.datamodel.entities.IUser;
import org.handrianj.corrie.editors.util.editors.ICorrieEditorInput;

/**
 * Prototype for the user manager editor input
 *
 * @author Heri Andrianjafy
 *
 * @param <U>
 */
public interface IUserManagerEditorInput<U extends IUser> extends ICorrieEditorInput<List<U>> {

}
