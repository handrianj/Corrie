package org.handrianj.corrie.usermanager.editor;

import java.util.List;

import org.handrianj.corrie.dblink.model.IUser;
import org.handrianj.corrie.editors.util.factories.IEditorInputFactory;

/**
 * Prototype for the user manager editor input factory
 * 
 * @author Heri Andrianjafy
 *
 * @param <U>
 */
public interface IUserManagerEditorInputFactory<U extends IUser> extends IEditorInputFactory<List<U>> {

}
