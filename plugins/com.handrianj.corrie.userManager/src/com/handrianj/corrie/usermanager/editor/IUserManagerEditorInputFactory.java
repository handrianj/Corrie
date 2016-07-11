package com.handrianj.corrie.usermanager.editor;

import java.util.List;

import com.handrianj.corrie.dblink.model.IUser;
import com.handrianj.corrie.editors.util.factories.IEditorInputFactory;

/**
 * Prototype for the user manager editor input factory
 * 
 * @author Heri Andrianjafy
 *
 * @param <U>
 */
public interface IUserManagerEditorInputFactory<U extends IUser> extends IEditorInputFactory<List<U>> {

}
