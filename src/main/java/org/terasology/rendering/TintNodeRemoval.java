/*
 * Copyright 2017 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.rendering;

import org.terasology.context.Context;
import org.terasology.entitySystem.systems.BaseComponentSystem;
import org.terasology.entitySystem.systems.RegisterSystem;
import org.terasology.registry.In;
import org.terasology.rendering.dag.api.RenderDagApiInterface;
import org.terasology.rendering.dag.gsoc.RenderDagApi;

// TODO NewAbstractNode to NewNode only!, no NewAbstractNode in here
// TODO Decide on what must by marked as @API or whitelisted (like nodes and such...use renderGraphAPI to access main DAG)
@RegisterSystem
public class TintNodeRemoval extends BaseComponentSystem {

    @In
    Context context;

    RenderDagApiInterface renderDagApi;

    @Override
    public void initialise() {
        super.initialise();
        renderDagApi = context.get(RenderDagApiInterface.class);

        // TODO SHOULD THIS BE ACCESSIBLE? - NO
        // RenderGraph rg = context.get(RenderGraph.class);

        // TODO SHOULD THIS BE POSSIBLE from within a module ??
        // NewNode finalPostProcessingNode = rg.findNode();
        // NewNode tintNode = rg.findNode("engine:tintNode");
        // NewNode outputToScreenNode = rg.findNode();

        renderDagApi.disconnectOutputFbo("engine:finalPostProcessingNode",1);
        renderDagApi.reconnectInputFboToOutput("engine:outputToScreenNode", 1, "engine:finalPostProcessingNode", 1);
        // renderDagApi.removeNode(new SimpleUri("engine:tintNode"));
        // TODO disconnect and remove from renderGraph

        // rg.addNode(new TintNode("aha",context));
        // System.out.println(tintNode.toString());
    }
}
