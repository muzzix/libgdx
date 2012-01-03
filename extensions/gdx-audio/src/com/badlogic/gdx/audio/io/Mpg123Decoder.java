package com.badlogic.gdx.audio.io;
/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/


import java.nio.ShortBuffer;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.SharedLibraryLoader;

/** A {@link Decoder} implementation that decodes MP3 files via libmpg123 natively.
 * 
 * @author mzechner */
public class Mpg123Decoder implements Decoder {
	static {
		new SharedLibraryLoader().load("gdx-audio");
	}
	
	public final long handle;

	/** Opens the given file for mp3 decoding. Throws an IllegalArugmentException in case the file could not be opened.
	 * 
	 * @param filename the filename */
	public Mpg123Decoder (FileHandle file) {
		if(file.type() != FileType.External && file.type() != FileType.Absolute)
			throw new IllegalArgumentException("File must be absolute or external!");
		handle = openFile(file.file().getAbsolutePath());
	}

	/** {@inheritDoc} */
	@Override
	public int readSamples (short[] samples, int offset, int numSamples) {
//		int read = readSamples(handle, samples, samples.capacity());
//		samples.position(0);
//		return read;
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public int skipSamples (int numSamples) {
		return skipSamples(handle, numSamples);
	}

	/** {@inheritDoc} */
	public int getChannels () {
		return getNumChannels(handle);
	}

	/** {@inheritDoc} */
	public int getRate () {
		return getRate(handle);
	}

	/** {@inheritDoc} */
	public float getLength () {
		return getLength(handle);
	}

	private native long openFile (String filename);

	private native int readSamples (long handle, ShortBuffer buffer, int numSamples);

	private native int skipSamples (long handle, int numSamples);

	private native int getNumChannels (long handle);

	private native int getRate (long handle);

	private native float getLength (long handle);

	private native void closeFile (long handle);

	/** {@inheritDoc} */
	@Override
	public void dispose () {
		closeFile(handle);
	}
}
