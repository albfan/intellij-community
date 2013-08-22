/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.psi.codeStyle;

import com.intellij.openapi.components.ServiceManager;
import org.jetbrains.annotations.Nullable;

/**
 * @author MYakovlev
 * Date: Jul 19, 2002
 */
public abstract class CodeStyleSchemes {

  public static CodeStyleSchemes getInstance(){
    CodeStyleSchemes schemes = ServiceManager.getService(CodeStyleSchemes.class);
    if (!schemes.isLoaded()) {
      schemes.loadSettings();
    }
    return schemes;
  }

  public abstract CodeStyleScheme[] getSchemes();

  public abstract CodeStyleScheme getCurrentScheme();

  public abstract void setCurrentScheme(CodeStyleScheme scheme);

  public abstract CodeStyleScheme createNewScheme(String preferredName, CodeStyleScheme parentScheme);

  public abstract void deleteScheme(CodeStyleScheme scheme);

  public abstract CodeStyleScheme findSchemeByName(String name);

  /**
   * Attempts to find a scheme with a given name or an alternative suitable scheme.
   * 
   * @param preferredSchemeName The scheme name to find or null for the currently selected scheme.
   * @return A found scheme or a default scheme if the scheme name was not found or, if neither exists or the scheme name is null, the
   *         currently selected scheme.
   */
  public CodeStyleScheme findPreferredScheme(@Nullable String preferredSchemeName) {
    CodeStyleScheme scheme = null;
    if (preferredSchemeName != null) {
      scheme = findSchemeByName(preferredSchemeName);
      if (scheme == null) {
        scheme = getDefaultScheme();
      }
    }
    if (scheme == null) {
      scheme = getCurrentScheme();
    }
    return scheme;
  }

  public abstract CodeStyleScheme getDefaultScheme();

  public abstract void addScheme(CodeStyleScheme currentScheme);

  public abstract boolean isLoaded();

  public abstract void loadSettings();
}

